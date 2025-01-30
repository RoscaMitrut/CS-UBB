import React, { useState, useEffect } from 'react';
import { 
  IonPage, 
  IonHeader, 
  IonToolbar, 
  IonTitle, 
  IonContent, 
  IonList, 
  IonItem, 
  IonLabel, 
  IonSegment, 
  IonSegmentButton, 
  IonInput, 
  IonButton, 
  IonToast,
  IonLoading
} from '@ionic/react';
import { StorageService } from './StorageService';
import { WebSocketService } from './WebSocketService';
import { ApiService } from './ApiService';

const OrderPage: React.FC = () => {
  const [menu, setMenu] = useState<any[]>([]);
  const [items, setItems] = useState<any[]>([]);
  const [filter, setFilter] = useState<string>('all');
  const [editingItemId, setEditingItemId] = useState<number | null>(null);
  const [table, setTable] = useState<string>('');
  const [toast, setToast] = useState<{
    message: string;
    color?: string;
    isOpen: boolean;
  }>({ message: '', isOpen: false });
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    const fetchInitialData = async () => {
      setLoading(true);
      try {
        const storedTable = await StorageService.get('table');
        if (!storedTable) {
          throw new Error('No table set');
        }
        setTable(storedTable);

        // First try local storage
        const storedMenu = await StorageService.getMenu();
        if (storedMenu) {
          setMenu(storedMenu);
          setItems(storedMenu.map(menuItem => ({ 
            ...menuItem, 
            quantity: 0 
          })));
        } else {
          // If no stored menu, fetch from WebSocket
          const fetchedMenu = await WebSocketService.getInstance().connectAndFetchMenu();
          setMenu(fetchedMenu);
          setItems(fetchedMenu.map(menuItem => ({ 
            ...menuItem, 
            quantity: 0 
          })));
          
          // Store fetched menu
          await StorageService.storeMenu(fetchedMenu);
        }
      } catch (error) {
        setToast({ 
          message: 'Failed to load menu', 
          color: 'danger', 
          isOpen: true 
        });
      } finally {
        setLoading(false);
      }
    };

    fetchInitialData();
  }, []);

  const updateQuantity = async (code: number, newQuantity: number) => {
    const updatedItems = items.map(item => 
      item.code === code 
        ? { ...item, quantity: newQuantity } 
        : item
    );
    setItems(updatedItems);
    await StorageService.storeItems(updatedItems);
  };

  const handleSubmitOrder = async () => {
    setSubmitting(true);
    try {
      const results = await ApiService.submitOrder(items, table);
      
      const updatedItems = items.map(item => {
        const result = results.find(r => r.item.code === item.code);
        return result?.status === 'rejected' 
          ? { ...item, submitError: result.reason.text }
          : { ...item, submitError: undefined };
      });

      setItems(updatedItems);
      await StorageService.storeItems(updatedItems);

      setToast({ 
        message: 'Order submitted', 
        color: 'success', 
        isOpen: true 
      });
    } catch (error) {
      setToast({ 
        message: 'Failed to submit order', 
        color: 'danger', 
        isOpen: true 
      });
    } finally {
      setSubmitting(false);
    }
  };

  const filteredItems = items.filter(item => 
    filter === 'all' || (filter === 'ordered' && item.quantity > 0)
  );

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Order for Table {table}</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonSegment value={filter} onIonChange={e => setFilter(e.detail.value as string)}>
          <IonSegmentButton value="all">All Items</IonSegmentButton>
          <IonSegmentButton value="ordered">Ordered Items</IonSegmentButton>
        </IonSegment>

        <IonLoading 
          isOpen={loading} 
          message="Loading menu..." 
        />

        <IonList>
          {filteredItems.map(item => (
            <IonItem key={item.code}>
              <IonLabel>
                <h2>{item.name}</h2>
                {editingItemId === item.code ? (
                  <IonInput 
                    type="number" 
                    value={item.quantity} 
                    onIonChange={e => updateQuantity(item.code, Number(e.detail.value))}
                    onBlur={() => setEditingItemId(null)}
                  />
                ) : (
                  <p 
                    onClick={() => setEditingItemId(item.code)}
                    style={{ color: item.submitError ? 'red' : 'inherit' }}
                  >
                    {item.submitError || `Quantity: ${item.quantity}`}
                  </p>
                )}
                <p>Price: ${item.price}</p>
                <p>Total: ${item.price * item.quantity}</p>
              </IonLabel>
            </IonItem>
          ))}
        </IonList>

        <IonButton 
          expand="block" 
          onClick={handleSubmitOrder}
          disabled={!items.some(item => item.quantity > 0)}
        >
          Submit Order
        </IonButton>

        <IonLoading 
          isOpen={submitting} 
          message="Submitting order..." 
        />

        <IonToast
          isOpen={toast.isOpen}
          onDidDismiss={() => setToast({ ...toast, isOpen: false })}
          message={toast.message}
          color={toast.color}
          duration={2000}
        />
      </IonContent>
    </IonPage>
  );
};

export default OrderPage;