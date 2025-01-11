// src/components/LogoutButton.tsx
import React from 'react';
import { useAuth } from '../AuthContext';
import { useHistory } from 'react-router-dom';

const HomeButton: React.FC = () => {
  const { logout } = useAuth();
  const history = useHistory();

  const handleHomeClick = () => {
    history.push('/home');
  };

  return (
    <button onClick={handleHomeClick} style={{ padding: '10px 20px' }}>
      Home
    </button>
  );
};

export default HomeButton;