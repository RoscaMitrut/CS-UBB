//Home.tsx
import {
	IonContent,
	IonHeader,
	IonPage,
	IonTitle,
	IonToolbar,
	IonButton,
  IonCard,
  IonCardContent,
} from "@ionic/react";
import { useHistory } from "react-router-dom";
import LogoutButton from "../components/LogoutButton";
import Example from "../components/Example";
const Home: React.FC = () => {
	const history = useHistory();

	const handleGoToLists = () => {
		history.push("/list");
	};

	return (
		<IonPage>
			<IonHeader>
				<IonToolbar>
					<IonTitle>Welcome to ToDoList</IonTitle>
					<LogoutButton></LogoutButton>
				</IonToolbar>
			</IonHeader>
			<IonContent fullscreen>
				<div style={{ textAlign: "center", padding: "20px" }}>
					<h1>Welcome to your ToDo List App!</h1>
					<p>Manage your tasks efficiently and stay organized.</p>
					<IonButton onClick={handleGoToLists}>My Lists</IonButton>
					<Example></Example>
				</div>
			</IonContent>
		</IonPage>
	);
};

export default Home;
