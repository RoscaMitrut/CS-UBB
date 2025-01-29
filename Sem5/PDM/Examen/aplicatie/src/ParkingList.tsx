// pages/ParkingList.tsx
import React, { useState, useEffect, useCallback } from 'react';
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  IonList,
  IonItem,
  IonLabel,
  IonButton,
  IonSearchbar,
  IonProgressBar,
  IonToast,
  IonIcon,
} from '@ionic/react';
import { car } from 'ionicons/icons';
import { ParkingSpace } from './types';

const ParkingList: React.FC = () => {
  const [spaces, setSpaces] = useState<ParkingSpace[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searchText, setSearchText] = useState('');
  const [expandedId, setExpandedId] = useState<number | null>(null);
  const [updateLoading, setUpdateLoading] = useState<number | null>(null);
  const [updateError, setUpdateError] = useState<string | null>(null);
  const username = localStorage.getItem('username');

  const connectWebSocket = useCallback(() => {
    const ws = new WebSocket('ws://localhost:3000');

    ws.onmessage = (event) => {
      const updatedSpace: ParkingSpace = JSON.parse(event.data);
      setSpaces(current =>
        current.map(space =>
          space.id === updatedSpace.id ? updatedSpace : space
        )
      );
    };

    ws.onclose = () => {
      // Retry connection after 5 seconds
      setTimeout(connectWebSocket, 5000);
    };

    return ws;
  }, []);

  const fetchSpaces = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch('http://localhost:3000/space');
      if (!response.ok) throw new Error('Failed to fetch parking spaces');
      const data = await response.json();
      setSpaces(data);
      localStorage.setItem('parkingSpaces', JSON.stringify(data));
    } catch (err) {
      setError('Failed to load parking spaces');
      const cached = localStorage.getItem('parkingSpaces');
      if (cached) {
        setSpaces(JSON.parse(cached));
      }
    } finally {
      setLoading(false);
    }
  };

  const updateSpace = async (id: number, status: 'taken' | 'free') => {
    setUpdateLoading(id);
    setUpdateError(null);
    try {
      const response = await fetch(`http://localhost:3000/space/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id,
          takenBy: status === 'taken' ? username : '',
          status
        }),
      });

      if (!response.ok) throw new Error('Failed to update space');
      const updatedSpace = await response.json();
      setSpaces(current =>
        current.map(space =>
          space.id === id ? updatedSpace : space
        )
      );
    } catch (err) {
      setUpdateError('Failed to update space');
    } finally {
      setUpdateLoading(null);
    }
  };

  useEffect(() => {
    fetchSpaces();
    const ws = connectWebSocket();
    return () => ws.close();
  }, [connectWebSocket]);

  const filteredSpaces = spaces.filter(space =>
    space.number.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <IonPage>
      
      <IonHeader>
        <IonToolbar>
          <IonTitle>Parking Spaces</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        {loading && <IonProgressBar type="indeterminate" />}
        
        <IonSearchbar
          value={searchText}
          onIonChange={e => setSearchText(e.detail.value!)}
          placeholder="Search by number"
        />

        {error && (
          <IonItem>
            <IonLabel color="danger">{error}</IonLabel>
            <IonButton onClick={fetchSpaces} slot="end">
              Retry
            </IonButton>
          </IonItem>
        )}

        <IonList>
          {filteredSpaces.map(space => (
            <IonItem
              key={space.id}
              onClick={() => setExpandedId(expandedId === space.id ? null : space.id)}
              color={
                space.takenBy === username
                  ? 'warning'
                  : !space.takenBy
                  ? 'success'
                  : undefined
              }
            >
              <IonIcon icon={car} slot="start" />
              <IonLabel>
                <h2>Space {space.number}</h2>
                {space.takenBy && <p>Taken by: {space.takenBy}</p>}
              </IonLabel>

              {expandedId === space.id && (
                <>
                  {updateLoading === space.id && <IonProgressBar type="indeterminate" />}
                  
                  {(!space.takenBy || space.takenBy === username) && (
                    <IonButton
                      slot="end"
                      onClick={(e) => {
                        e.stopPropagation();
                        updateSpace(
                          space.id,
                          space.takenBy === username ? 'free' : 'taken'
                        );
                      }}
                      disabled={updateLoading === space.id}
                    >
                      {space.takenBy === username ? 'Release' : 'Take'}
                    </IonButton>
                  )}
                </>
              )}
            </IonItem>
          ))}
        </IonList>
          
        <IonToast
          isOpen={!!updateError}
          message={updateError || ''}
          duration={3000}
          buttons={[
            {
              text: 'Retry',
              handler: () => {
                if (expandedId !== null) {
                  const space = spaces.find(s => s.id === expandedId);
                  if (space) {
                    updateSpace(
                      expandedId,
                      space.takenBy === username ? 'free' : 'taken'
                    );
                  }
                }
              }
            }
          ]}
        />

      </IonContent>

    </IonPage>
  );
};

export default ParkingList;