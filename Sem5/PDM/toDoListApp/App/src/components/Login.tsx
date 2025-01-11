// src/components/Login.tsx
import React,{useState} from 'react';
import { useAuth } from '../AuthContext';
import { useHistory } from 'react-router-dom';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');  
  const [error, setError] = useState('');
  const { login } = useAuth();
  const history = useHistory();

  const handleLogin = async () => {
    try {
      setError('');
      await login(username, password);
      history.push('/home'); // Redirect to home upon successful login
    } catch (err) {
      setError('Invalid username or password');
    }
  };

  return (
    <div style={{ padding: "20px", textAlign: "center" }}>
      <h1>Login</h1>
      <input
        type="text"
        placeholder="Enter your username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        style={{ padding: "10px", marginBottom: "10px", width: "100%" }}
      />
      <input
        type="password"
        placeholder="Enter your password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        style={{ padding: "10px", marginBottom: "10px", width: "100%" }}
      />
      <button onClick={handleLogin} style={{ padding: "10px 20px" }}>
        Log in
      </button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default Login;
