from flask import Flask, jsonify, request, send_from_directory, url_for
from flask_jwt_extended import create_access_token, jwt_required, JWTManager
from flask_cors import CORS
import json
import os
from werkzeug.utils import secure_filename
import uuid

# Initialize the app
app = Flask(__name__)
app.config["JWT_SECRET_KEY"] = "your_secret_key"  # Replace with a secure key
jwt = JWTManager(app)
CORS(app, resources={r"/*": {"origins": "*"}})

# Paths for data storage
DATA_DIR = "data"
USERS_FILE = os.path.join(DATA_DIR, "users.json")
TASKS_FILE = os.path.join(DATA_DIR, "tasks.json")
UPLOAD_FOLDER = os.path.join(DATA_DIR, "uploads")
ALLOWED_EXTENSIONS = {'jpg', 'jpeg', 'png'}

# Ensure upload directory exists
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

# Utility Functions
def load_users():
    """Load users from the JSON file."""
    if os.path.exists(USERS_FILE):
        with open(USERS_FILE, "r") as file:
            return json.load(file)
    return {}

def save_users(users):
    """Save users to the JSON file."""
    os.makedirs(DATA_DIR, exist_ok=True)
    with open(USERS_FILE, "w") as file:
        json.dump(users, file, indent=4)
    print("Users saved:", users)  # Log to the console when users are saved

def load_tasks():
    """Load tasks from the JSON file."""
    if os.path.exists(TASKS_FILE):
        with open(TASKS_FILE, "r") as file:
            return json.load(file)
    return []

def save_tasks(tasks):
    """Save tasks to the JSON file."""
    os.makedirs(DATA_DIR, exist_ok=True)
    with open(TASKS_FILE, "w") as file:
        json.dump(tasks, file, indent=4)
    print("Tasks saved:", tasks)  # Log to the console when tasks are saved

def authenticate(username, password):
    """Authenticate user credentials."""
    users = load_users()
    if users.get(username) == password:
        print(f"User {username} authenticated successfully.")
        return create_access_token(identity=username)
    print(f"Failed authentication attempt for {username}.")
    return None


# API Routes
@app.route("/login", methods=["POST"])
def login():
    data = request.json
    username = data.get("username")
    password = data.get("password")

    token = authenticate(username, password)
    if token:
        print(f"Login successful for {username}.")
        return jsonify({"token": token,"ownerId": username}), 200
    print(f"Login failed for {username}. Invalid credentials.")
    return jsonify({"msg": "Invalid credentials"}), 401

@app.route("/tasks", methods=["GET"])
@jwt_required()
def get_tasks():
    owner_id = request.args.get("ownerId")
    tasks = load_tasks()
    user_tasks = [task for task in tasks if task["ownerId"] == owner_id]
    print(f"Tasks retrieved for user {owner_id}.")
    return jsonify(user_tasks), 200

@app.route("/tasks", methods=["POST"])
@jwt_required()
def add_task():
    data = request.json
    tasks = load_tasks()

    new_task = {
        "id": str(len(tasks) + 1),
        "ownerId": data["ownerId"],
        "title": data["title"],
        "date": data["date"],
        "isDone": data["isDone"],
        "imageUrl": data.get("imageUrl")  # Add support for imageUrl
    }
    tasks.append(new_task)
    save_tasks(tasks)
    print(f"Task added: {new_task}")
    return jsonify(new_task), 201

@app.route("/tasks/<task_id>", methods=["PUT"])
@jwt_required()
def update_task(task_id):
    data = request.json
    tasks = load_tasks()

    task_to_update = next((task for task in tasks if task["id"] == task_id), None)
    if not task_to_update:
        print(f"Task {task_id} not found for update.")
        return jsonify({"msg": "Task not found"}), 404

    task_to_update.update({
        "title": data.get("title", task_to_update["title"]),
        "date": data.get("date", task_to_update["date"]),
        "isDone": data.get("isDone", task_to_update["isDone"]),
        "imageUrl": data.get("imageUrl", task_to_update.get("imageUrl"))  # Add support for imageUrl
    })

    save_tasks(tasks)
    print(f"Task updated: {task_to_update}")
    return jsonify(task_to_update), 200

@app.route("/upload", methods=["POST"])
def upload_file():
    if 'image' not in request.files:
        return jsonify({"error": "No file part"}), 400
    
    file = request.files['image']
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400

    if file and allowed_file(file.filename):
        # Generate a unique filename
        filename = str(uuid.uuid4()) + '.' + file.filename.rsplit('.', 1)[1].lower()
        file_path = os.path.join(UPLOAD_FOLDER, filename)
        file.save(file_path)
        
        server_url = request.host_url.rstrip('/')  # Gets base URL like http://10.131.8.157:5000
        image_url = f"{server_url}/uploads/{filename}"
        return jsonify(image_url), 200
    
    return jsonify({"error": "Invalid file type"}), 400

@app.route("/uploads/<filename>")
def uploaded_file(filename):
    return send_from_directory(UPLOAD_FOLDER, filename, as_attachment=False)

# Initialize Data Files if Missing
if __name__ == "__main__":
    if not os.path.exists(DATA_DIR):
        os.makedirs(DATA_DIR)
        print(f"Created data directory: {DATA_DIR}")

    # Initialize users and tasks if files don't exist
    if not os.path.exists(USERS_FILE):
        save_users({
            "user1": "password1",
            "user2": "password2"
        })
        print(f"Users initialized in {USERS_FILE}")

    if not os.path.exists(TASKS_FILE):
        save_tasks([
            {"id": "1", "ownerId": "user1", "title": "Buy groceries", "date": "2024-12-06", "isDone": False, "imageUrl": None},
            {"id": "2", "ownerId": "user1", "title": "Call mom", "date": "2024-12-06", "isDone": True, "imageUrl": None},
            {"id": "3", "ownerId": "user2", "title": "Finish project", "date": "2024-12-07", "isDone": False, "imageUrl": None}
        ])
        print(f"Tasks initialized in {TASKS_FILE}")

    # Run the server
    app.run(host="0.0.0.0", port=5000, debug=True)