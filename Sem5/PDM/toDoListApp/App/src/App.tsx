//App.tsx
import { Redirect, Route, Switch } from "react-router-dom";
import { IonApp, IonRouterOutlet, setupIonicReact } from "@ionic/react";
import { IonReactRouter } from "@ionic/react-router";

import React, { useEffect, useState } from "react";
import {
	IonContent,
	IonHeader,
	IonPage,
	IonTitle,
	IonToolbar,
} from "@ionic/react";
import { io } from "socket.io-client";

import Home from "./pages/Home";
import ListPage from "./pages/ListPage";
/* Core CSS required for Ionic components to work properly */
import "@ionic/react/css/core.css";

/* Basic CSS for apps built with Ionic */
import "@ionic/react/css/normalize.css";
import "@ionic/react/css/structure.css";
import "@ionic/react/css/typography.css";

/* Optional CSS utils that can be commented out */
import "@ionic/react/css/padding.css";
import "@ionic/react/css/float-elements.css";
import "@ionic/react/css/text-alignment.css";
import "@ionic/react/css/text-transformation.css";
import "@ionic/react/css/flex-utils.css";
import "@ionic/react/css/display.css";

/* import '@ionic/react/css/palettes/dark.always.css'; */
/* import '@ionic/react/css/palettes/dark.class.css'; */
import "@ionic/react/css/palettes/dark.system.css";

/* Theme variables */
import "./theme/variables.css";
import ProtectedRoute from "./components/ProtectedRoute";
import NetworkStatus from "./components/NetworkStatus";
import Login from "./components/Login";
import { AuthProvider } from "./AuthContext";

const socket = io("http://127.0.0.1:5000");

setupIonicReact();

const App: React.FC = () => {
	const [notifications, setNotifications] = useState<string[]>([]);

	const [refreshKey, setRefreshKey] = useState(0);

	useEffect(() => {
		socket.on("response", (data) => {
			console.log(data);
		});

		socket.on("notification_response", (data) => {
			setNotifications((prev) => [...prev, data.data]);
			setRefreshKey((prevKey) => prevKey + 1);
			//alert(data.data); // Show an alert when a notification is received
		});

		// Clean up the socket connection when the component unmounts
		return () => {
			socket.off("response");
			socket.off("notification_response");
		};
	}, []);

	return (
		<div key={refreshKey}>
			<IonApp>
				<AuthProvider>
					<IonReactRouter>
						<NetworkStatus/>
						<Switch>
							<Route path="/login" component={Login} />
							<ProtectedRoute path={"/home"} component={Home} />
							<ProtectedRoute path="/list" component={ListPage} />
							<ProtectedRoute
								exact
								path="/list/:id"
								component={ListPage}
							/>
							<Redirect exact from="/" to="/login" />
						</Switch>
					</IonReactRouter>
				</AuthProvider>
			</IonApp>
		</div>
	);
};

export default App;
