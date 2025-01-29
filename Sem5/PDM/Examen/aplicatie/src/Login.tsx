import React, { useState, useEffect } from 'react';
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
} from '@ionic/react';
import { useHistory } from 'react-router-dom';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const history = useHistory();

  useEffect(() => {
    const savedUsername = localStorage.getItem('username');
    if (savedUsername) {
      history.push('/parking');
    }
  }, [history]);

  const handleNext = () => {
    if (username.trim()) {
      localStorage.setItem('username', username);
      history.push('/parking');
    }
  };

  return (
    <IonPage>

      <IonHeader>
        <IonToolbar>
          <IonTitle>Login</IonTitle>
        </IonToolbar>
      </IonHeader>
      
      <IonContent className="ion-padding">
    
        <IonItem>
          <IonLabel position="stacked">Username</IonLabel>
          <IonInput
            value={username}
            onIonChange={e => setUsername(e.detail.value!)}
            placeholder="Enter username"
          />
        </IonItem>
    
        <IonButton
          expand="block"
          className="ion-margin-top"
          onClick={handleNext}
          disabled={!username.trim()}
        >
          Next
        </IonButton>
      
      </IonContent>
    
    </IonPage>
  );
};

export default Login;
