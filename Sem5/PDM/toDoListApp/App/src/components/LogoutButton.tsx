// src/components/LogoutButton.tsx
import React from 'react';
import { useAuth } from '../AuthContext';
import { useHistory } from 'react-router-dom';

const LogoutButton: React.FC = () => {
  const { logout } = useAuth();
  const history = useHistory();

  const handleLogout = () => {
    logout();
    history.push('/login');
  };

  return (
    <button onClick={handleLogout} style={{ padding: '10px 20px' }}>
      Log out
    </button>
  );
};

export default LogoutButton;
