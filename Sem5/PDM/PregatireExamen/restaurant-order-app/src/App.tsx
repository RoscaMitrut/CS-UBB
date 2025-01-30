import React, { useEffect, useState } from 'react';
import { Redirect, Route } from 'react-router-dom';
import { 
  IonApp, 
  IonRouterOutlet, 
  setupIonicReact 
} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';

import OrderPage from './OrderPage';
import TableSetupPage from './TableSetupPage';
import { StorageService } from './StorageService';

setupIonicReact();

const App: React.FC = () => {
  const [tableSet, setTableSet] = useState<boolean>(false);

  useEffect(() => {
    const checkTableSetup = async () => {
      const table = await StorageService.get('table');
      setTableSet(!!table);
    };
    checkTableSetup();
  }, []);

  return (
    <IonApp>
      <IonReactRouter>
        <IonRouterOutlet>
          <Route exact path="/table-setup">
            <TableSetupPage onTableSet={() => setTableSet(true)} />
          </Route>
          <Route exact path="/order">
            {tableSet ? <OrderPage /> : <Redirect to="/table-setup" />}
          </Route>
          <Route exact path="/">
            <Redirect to={tableSet ? "/order" : "/table-setup"} />
          </Route>
        </IonRouterOutlet>
      </IonReactRouter>
    </IonApp>
  );
};

export default App;