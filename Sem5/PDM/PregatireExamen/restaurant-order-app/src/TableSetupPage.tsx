import React, { useState, useEffect } from 'react';
import { 
  IonContent, 
  IonHeader, 
  IonPage, 
  IonTitle, 
  IonToolbar, 
  IonInput, 
  IonButton, 
  IonLoading 
} from '@ionic/react';
import { StorageService } from './StorageService';
import { useHistory } from 'react-router-dom';

interface TableSetupPageProps {
  onTableSet: () => void;
}

const TableSetupPage: React.FC<TableSetupPageProps> = ({ onTableSet }) => {
  const [table, setTable] = useState<string>('');
  const [isTableLocked, setIsTableLocked] = useState<boolean>(false);
  const [showLoading, setShowLoading] = useState(false);
  const history = useHistory();

  useEffect(() => {
    const checkExistingTable = async () => {
      try {
        const storedTable = await StorageService.get('table');
        if (storedTable) {
          setTable(storedTable);
          setIsTableLocked(true);
        }
      } catch (error) {
        console.error('Error checking table:', error);
      }
    };
    checkExistingTable();
  }, []);

  const handleSetTable = async () => {
    if (isTableLocked) {
      // If table is already set, just navigate to order page
      history.push('/order');
      return;
    }

    if (table.trim()) {
      try {
        setShowLoading(true);
        await StorageService.set('table', table);
        onTableSet();
        history.push('/order');
      } catch (error) {
        console.error('Error setting table:', error);
      } finally {
        setShowLoading(false);
      }
    }
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Set Table</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        <IonInput 
          value={table}
          onIonChange={e => setTable(e.detail.value!)}
          placeholder="Enter Table Number"
          disabled={isTableLocked}
        />
        <IonButton 
          expand="block" 
          onClick={handleSetTable}
          color={isTableLocked ? "secondary" : "primary"}
        >
          {isTableLocked ? "Continue to Order" : "Set Table"}
        </IonButton>
        <IonLoading
          isOpen={showLoading}
          onDidDismiss={() => setShowLoading(false)}
          message={'Setting table...'}
        />
      </IonContent>
    </IonPage>
  );
};

export default TableSetupPage;