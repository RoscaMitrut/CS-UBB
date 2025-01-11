from flask import Flask, jsonify, request
from flask_cors import CORS
from flask_socketio import SocketIO, emit
import json
import os
import time
import jwt
import datetime

SECRET_KEY = "cheie_foarte_securizata_si_secreta"

app = Flask(__name__)
CORS(app, resources={r'/*': {'origins': '*'}})

socketio = SocketIO(app, cors_allowed_origins="*")

notifications = []

USERS_FILE = 'users.txt'
TASKS_FILE = 'tasks.txt'

def load_users():
    if not os.path.exists(USERS_FILE):
        return {}

    users = {}
    with open(USERS_FILE, "r") as file:
        for line in file:
            line = line.strip()
            if line:
                user_id, username, password = line.split(":")
                users[username] = {"id": user_id, "password": password}
    return users

def load_tasks():
    if not os.path.exists(TASKS_FILE):
        return {}

    with open(TASKS_FILE, 'r') as file:
        try:
            return json.load(file)
        except json.JSONDecodeError:
            return {}

def save_tasks(tasks):
    with open(TASKS_FILE, 'w') as file:
        json.dump(tasks, file)

def get_user_from_token():
    auth_header = request.headers.get("Authorization")
    if not auth_header or not auth_header.startswith("Bearer "):
        return None

    token = auth_header.split(" ")[1]
    try:
        decoded = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        return decoded  
    except jwt.ExpiredSignatureError:
        return None
    except jwt.InvalidTokenError:
        return None

@app.route('/tasks', methods=['GET'])
def get_tasks():
    user = get_user_from_token()
    if not user:
        return jsonify({"error": "Invalid or missing token"}), 401

    user_id = user['id']
    tasks = load_tasks()
    user_tasks = tasks.get(user_id, [])
    return jsonify(user_tasks)

@app.route('/tasks', methods=['POST'])
def add_task():
    user = get_user_from_token()
    if not user:
        return jsonify({"error": "Invalid or missing token"}), 401

    user_id = user['id']
    new_task = request.json
    tasks = load_tasks()
    user_tasks = tasks.get(user_id, [])

    new_task['id'] = max([task['id'] for task in user_tasks], default=0) + 1
    new_task['owner_id'] = user_id
    user_tasks.append(new_task)
    tasks[user_id] = user_tasks
    save_tasks(tasks)

    send_notifications()
    return jsonify(new_task), 201

@app.route('/tasks/<int:task_id>', methods=['PUT'])
def edit_task(task_id):
    user = get_user_from_token()
    if not user:
        return jsonify({"error": "Invalid or missing token"}), 401

    user_id = user['id']
    tasks = load_tasks()
    user_tasks = tasks.get(user_id, [])
    task = next((task for task in user_tasks if task['id'] == task_id), None)

    if task:
        updated_task = request.json
        task.update(updated_task)
        tasks[user_id] = user_tasks
        save_tasks(tasks)
        send_notifications()
        return jsonify(task)
    return jsonify({'error': 'Task not found or access denied'}), 404

@app.route('/tasks/<int:task_id>', methods=['DELETE'])
def delete_task(task_id):
    user = get_user_from_token()
    if not user:
        return jsonify({"error": "Invalid or missing token"}), 401

    user_id = user['id']
    tasks = load_tasks()
    user_tasks = tasks.get(user_id, [])
    task = next((task for task in user_tasks if task['id'] == task_id), None)

    if task:
        user_tasks = [t for t in user_tasks if t['id'] != task_id]
        tasks[user_id] = user_tasks
        save_tasks(tasks)
        send_notifications()
        return jsonify({'message': 'Task deleted successfully'})
    return jsonify({'error': 'Task not found or access denied'}), 404

def send_notifications():
    message = f"Server notification at {time.strftime('%Y-%m-%d %H:%M:%S')}"
    notifications.append(message)
    socketio.emit('notification_response', {'data': message})

@app.route("/login", methods=["POST"])
def login():
    data = request.json
    username = data.get("username")
    password = data.get("password")

    users = load_users()
    user = users.get(username)
    if user and user["password"] == password:
        token = jwt.encode(
            {
                "id": user["id"],
                "username": username,
                "exp": datetime.datetime.utcnow() + datetime.timedelta(hours=1),
            },
            SECRET_KEY,
            algorithm="HS256",
        )
        return jsonify({"token": token}), 200
    else:
        return jsonify({"error": "Invalid credentials"}), 401

@socketio.on('connect')
def handle_connect():
    print('Client connected')
    emit('response', {'data': 'Connected to the server!'})

if __name__ == '__main__':
    socketio.run(app, debug=True, allow_unsafe_werkzeug=True)
