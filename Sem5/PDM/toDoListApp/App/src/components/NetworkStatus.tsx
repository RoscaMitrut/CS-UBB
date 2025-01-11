//NetworkStatus.tsx
import React, { useEffect, useState } from "react";
import { Network } from "@capacitor/network";
import { IonIcon, IonToast } from "@ionic/react";
import { wifiOutline } from "ionicons/icons";

const NetworkStatus: React.FC = () => {
	const [isOnline, setIsOnline] = useState(true);
	const [toastMessage, setToastMessage] = useState("");

	useEffect(() => {
		const checkNetworkStatus = async () => {
			const status = await Network.getStatus();
			setIsOnline(status.connected);
			setToastMessage(
				status.connected ? "You are online" : "You are offline"
			);
		};

		checkNetworkStatus();

		let networkListener: any;

		const setupNetworkListener = async () => {
			networkListener = await Network.addListener(
				"networkStatusChange",
				async (status) => {
					setIsOnline(status.connected);
					setToastMessage(
						status.connected ? "You are online" : "You are offline"
					);
				}
			);
		};

		setupNetworkListener();

		// Cleanup listener on unmount
		return () => {
			if (networkListener) {
				networkListener.remove();
			}
		};
	}, []);

	return (
		<>
			<IonIcon
				icon={isOnline ? wifiOutline : wifiOutline}
				style={{
					position: "absolute",
					top: "16px",
					right: "56px",
					fontSize: "24px",
					color: isOnline ? "green" : "red",
					zIndex: 1000,
				}}
			/>
			<IonToast
				isOpen={!!toastMessage}
				message={toastMessage}
				duration={1000}
				onDidDismiss={() => setToastMessage("")}
			/>
		</>
	);
};

export default NetworkStatus;
