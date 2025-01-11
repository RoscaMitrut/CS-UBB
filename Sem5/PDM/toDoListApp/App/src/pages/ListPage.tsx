import {
	IonContent,
	IonHeader,
	IonPage,
	IonTitle,
	IonToolbar,
	IonButtons,
	IonButton,
	IonIcon,
	IonModal,
	IonList,
	IonItem,
	IonLabel,
	IonInput,
	IonFooter,
	IonCheckbox,
	IonSegment,
	IonSegmentButton,
	IonInfiniteScroll,
	IonInfiniteScrollContent,
	IonImg,
	IonThumbnail,
	createAnimation,
	IonSearchbar,
	IonToast,
} from "@ionic/react";
import { add, create, trash, camera, map } from "ionicons/icons";
import { useEffect, useState } from "react";

import HomeButton from "../components/HomeButton";
import axios from "axios";

import {
	MapContainer,
	TileLayer,
	Marker,
	Popup,
	useMapEvents,
} from "react-leaflet";
import "leaflet/dist/leaflet.css";

// Define task interface with location property
interface Task {
	id: number;
	title: string;
	date: string; // Dates stored as strings for compatibility
	done: boolean;
	imageUrl?: string; // Optional image URL
	location?: { lat: number; lng: number }; // Optional location
}

// Helper functions to load/save tasks from/to localStorage
const loadTasksFromLocalStorage = (): Task[] => {
	const storedTasks = localStorage.getItem("tasks");
	return storedTasks ? JSON.parse(storedTasks) : [];
};

const saveTasksToLocalStorage = (tasks: Task[]): void => {
	localStorage.setItem("tasks", JSON.stringify(tasks));
};

// Location selection component using Leaflet
const LocationSelector: React.FC<{
	onLocationSelect: (lat: number, lng: number) => void;
}> = ({ onLocationSelect }) => {
	const [position, setPosition] = useState<{
		lat: number;
		lng: number;
	} | null>(null);

	const MapEvents = () => {
		const map = useMapEvents({
			click(e) {
				setPosition(e.latlng);
			},
		});
		return null;
	};

	return (
		<MapContainer
			center={[51.505, -0.09]}
			zoom={16}
			style={{ height: "400px", width: "100%" }}
		>
			<TileLayer
				url="https://tile.thunderforest.com/outdoors/{z}/{x}/{y}.png?apikey=189e2b815d834953b41e923a7c303ba0"
				attribution="&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
			/>
			<MapEvents />
			{position && (
				<Marker position={position}>
					<Popup>
						<IonButton
							onClick={() =>
								position &&
								onLocationSelect(position.lat, position.lng)
							}
						>
							SelectLocation
						</IonButton>
					</Popup>
				</Marker>
			)}
		</MapContainer>
	);
};

const ListPage: React.FC = () => {
	const [showModal, setShowModal] = useState(false);
	const [loc, setLoc] = useState<{ lat: number; lng: number } | undefined>({
		lat: 0,
		lng: 0,
	});
	const [showMap, setShowMap] = useState(false);
	const [tasks, setTasks] = useState<Task[]>([]); // All tasks
	const [visibleTasks, setVisibleTasks] = useState<Task[]>([]); // Tasks to display
	const [newTask, setNewTask] = useState<{
		title: string;
		done: boolean;
		imageUrl?: string;
		location?: { lat: number; lng: number }; // Add location to new task state
	}>({
		title: "",
		done: false,
	});
	const [editTaskId, setEditTaskId] = useState<number | null>(null);
	const [searchTerm, setSearchTerm] = useState<string>("");
	const [filterDone, setFilterDone] = useState<string>("all");

	const itemsPerBatch = 7; // Number of tasks to load per scroll

	// Fetch tasks from the API
	useEffect(() => {
		const timer = setTimeout(() => {
			axios
				.get("http://127.0.0.1:5000/tasks")
				.then((response) => {
					const fetchedTasks = response.data.map((task: any) => ({
						...task,
						date: new Date(task.date),
					}));
					setTasks(fetchedTasks);
					setVisibleTasks(fetchedTasks.slice(0, itemsPerBatch));
					saveTasksToLocalStorage(fetchedTasks); // Sync with local storage
				})
				.catch((error) => {
					console.error(
						"There was an error fetching the tasks:",
						error
					);
					// Fallback to localStorage if fetch fails (e.g., no internet)
					const localTasks = loadTasksFromLocalStorage();
					setTasks(localTasks);
					setVisibleTasks(localTasks.slice(0, itemsPerBatch));
				});
		}, 200);

		return () => clearTimeout(timer);
	}, []);

	useEffect(() => {
		const handleOnlineStatus = () => {
			// Push any local changes to the server when online
			const localTasks = loadTasksFromLocalStorage();
			localTasks.forEach((task) => {
				if (!task.id) {
					// Sync new tasks with the server
					axios
						.post("http://127.0.0.1:5000/tasks", task)
						.then(() => {
							console.log("Synced task to server:", task);
						})
						.catch((error) =>
							console.error("Error syncing task:", error)
						);
				}
			});
		};

		window.addEventListener("online", handleOnlineStatus);

		return () => {
			window.removeEventListener("online", handleOnlineStatus);
		};
	}, []);

	// Load more tasks on infinite scroll
	const loadMoreTasks = (event: CustomEvent<void>) => {
		setTimeout(() => {
			const nextBatch = tasks.slice(
				visibleTasks.length,
				visibleTasks.length + itemsPerBatch
			);
			setVisibleTasks((prevTasks) => [...prevTasks, ...nextBatch]);
			(event.target as HTMLIonInfiniteScrollElement).complete();
		}, 500);
	};

	// Handle saving (adding/editing) a task
	const handleSaveTask = async () => {
		if (!newTask.title) return;

		const currentDate = new Date();

		const taskData = {
			...newTask,
			date: currentDate.toISOString(),
		};

		try {
			let updatedTasks: Task[];

			if (editTaskId !== null) {
				// Update existing task
				const response = await axios.put(
					`http://127.0.0.1:5000/tasks/${editTaskId}`,
					taskData
				);
				const updatedTask = response.data;
				updatedTasks = tasks.map((task) =>
					task.id === editTaskId ? updatedTask : task
				);
			} else {
				// Create new task
				const response = await axios.post(
					"http://127.0.0.1:5000/tasks",
					taskData
				);
				const createdTask = response.data;
				updatedTasks = [
					...tasks,
					{ ...createdTask, date: currentDate },
				];
			}

			// Update tasks state and save to local storage
			setTasks(updatedTasks);
			saveTasksToLocalStorage(updatedTasks);
		} catch (error) {
			console.error("Error saving task:", error);
		}
	};

	// useEffect(() => {
	// 	let networkListener: any;

	// 	const initialize = async () => {
	// 		try {
	// 			// Check network status and load tasks
	// 			const status = await Network.getStatus();
	// 			setIsOnline(status.connected);

	// 			const storedTasks = await loadTasks();
	// 			setTasks(storedTasks);

	// 			if (status.connected) await syncUnsyncedTasks();

	// 			networkListener = Network.addListener(
	// 				"networkStatusChange",
	// 				async (status) => {
	// 					setIsOnline(status.connected);
	// 					setToastMessage(
	// 						status.connected
	// 							? "You're back online! Syncing tasks..."
	// 							: "You're offline. Changes will sync later."
	// 					);

	// 					if (status.connected) await syncUnsyncedTasks();
	// 				}
	// 			);
	// 		} catch (error) {
	// 			console.error("Initialization error:", error);
	// 			setToastMessage("An error occurred during initialization.");
	// 		}
	// 	};

	// 	initialize();

	// 	return () => {
	// 		if (networkListener) networkListener.remove();
	// 	};
	// }, []);

	// const handleSaveTask = async () => {
	// 	if (!newTask.title.trim()) return;

	// 	const now = new Date().toISOString();
	// 	const taskData: Task = {
	// 		id: editTaskId || Date.now(),
	// 		title: newTask.title,
	// 		date: now,
	// 		done: newTask.done,
	// 		synced: isOnline,
	// 	};

	// 	const updatedTasks = editTaskId
	// 		? tasks.map((task) => (task.id === editTaskId ? taskData : task))
	// 		: [...tasks, taskData];

	// 	setTasks(updatedTasks);
	// 	await saveTasksLocally(updatedTasks);

	// 	if (isOnline) await syncUnsyncedTasks();

	// 	setNewTask({ title: "", done: false, imageUrl: undefined });
	// 	setEditTaskId(null);
	// 	setShowModal(false);
	// };

	const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const file = event.target.files?.[0];
		if (file) {
			const imageUrl = URL.createObjectURL(file);
			setNewTask({ ...newTask, imageUrl });
		}
	};

	// Handle deleting a task
	const handleDeleteTask = (taskId: number) => {
		axios
			.delete(`http://127.0.0.1:5000/tasks/${taskId}`)
			.then(() => {
				const updatedTasks = tasks.filter((task) => task.id !== taskId);
				setTasks(updatedTasks);
				saveTasksToLocalStorage(updatedTasks); // Sync with local storage
			})
			.catch((error) => console.error("Error deleting task:", error));
	};

	// Handle toggling the 'done' status of a task
	const handleToggleDone = (taskId: number) => {
		const taskToUpdate = tasks.find((task) => task.id === taskId);
		if (taskToUpdate) {
			axios
				.put(`http://127.0.0.1:5000/tasks/${taskId}`, {
					...taskToUpdate,
					done: !taskToUpdate.done,
				})
				.then((response) => {
					const updatedTask = response.data;
					const updatedTasks = tasks.map((task) =>
						task.id === taskId ? updatedTask : task
					);
					setTasks(updatedTasks);
					saveTasksToLocalStorage(updatedTasks); // Sync with local storage
				})
				.catch((error) =>
					console.error("Error toggling task done status:", error)
				);
		}
	};

	// const handleToggleDone = async (taskId: number) => {
	// 	const updatedTasks = tasks.map((task) =>
	// 		task.id === taskId
	// 			? {
	// 					...task,
	// 					done: !task.done,
	// 					synced: isOnline ? task.synced : false,
	// 			  }
	// 			: task
	// 	);

	// 	setTasks(updatedTasks);
	// 	await saveTasksLocally(updatedTasks);

	// 	if (isOnline) await syncUnsyncedTasks();
	// };

	const handleEditTask = (taskId: number) => {
		const taskToEdit = tasks.find((task) => task.id === taskId);
		if (taskToEdit) {
			setNewTask({
				title: taskToEdit.title,
				done: taskToEdit.done,
				imageUrl: taskToEdit.imageUrl,
				location: taskToEdit.location,
			});

			setEditTaskId(taskId);
			setShowModal(true);
		}
	};
	const captureImage = () => {
		const input = document.createElement("input");
		input.type = "file";
		input.accept = "image/*";
		input.capture = "camera";
		input.onchange = handleImageChange;

		/*input.onchange = (event: Event) => {
			const target = event.target as HTMLInputElement;
			if (target?.files?.length) {
				const file = target.files[0];
				handleImageChange; // Call the parent function
			}
		};
*/
		input.click();
	};
	// Render Tasks with Images
	const filteredTasks = visibleTasks.filter((task) => {
		const matchesSearch = task.title
			.toLowerCase()
			.includes(searchTerm.toLowerCase());
		const matchesDoneFilter =
			filterDone === "all" ||
			(filterDone === "done" ? task.done : !task.done);
		return matchesSearch && matchesDoneFilter;
	});
	const enterAnimation = (baseEl: HTMLElement) => {
		const root = baseEl.shadowRoot!;

		const backdropAnimation = createAnimation()
			.addElement(root.querySelector("ion-backdrop")!)
			.fromTo("opacity", "0.01", "var(--backdrop-opacity)");

		const wrapperAnimation = createAnimation()
			.addElement(root.querySelector(".modal-wrapper")!)
			.keyframes([
				{ offset: 0, opacity: "0", transform: "scale(0)" },
				{ offset: 1, opacity: "0.99", transform: "scale(1)" },
			]);

		return createAnimation()
			.addElement(baseEl)
			.easing("ease-out")
			.duration(500)
			.addAnimation([backdropAnimation, wrapperAnimation]);
	};

	const leaveAnimation = (baseEl: HTMLElement) => {
		return enterAnimation(baseEl).direction("reverse");
	};
	return (
		<IonPage>
			<IonHeader>
				<IonToolbar>
					<IonTitle>ToDoList</IonTitle>
					<HomeButton />
					<IonButtons slot="end">
						<IonButton onClick={() => setShowModal(true)}>
							<IonIcon icon={add} />
						</IonButton>
					</IonButtons>
				</IonToolbar>
				{/* <IonToolbar>
					<IonSearchbar
						value={searchQuery}
						onIonInput={(e) => setSearchQuery(e.detail.value!)}
						placeholder="Search tasks"
					/>
					<IonSegment
						value={filter}
						onIonChange={(e) =>
							setFilter(
								e.detail.value as
									| "all"
									| "completed"
									| "pending"
							)
						}
					>
						<IonSegmentButton value="all">All</IonSegmentButton>
						<IonSegmentButton value="completed">
							Completed
						</IonSegmentButton>
						<IonSegmentButton value="pending">
							Pending
						</IonSegmentButton>
					</IonSegment>
				</IonToolbar> */}
			</IonHeader>
			<IonContent fullscreen>
				{/* Search Section */}
				<IonItem>
					<IonInput
						value={searchTerm}
						placeholder="Search tasks..."
						onIonInput={(e) => setSearchTerm(e.detail.value!)}
					/>
				</IonItem>

				{/* Filter Section with IonSegment */}
				<IonSegment
					value={filterDone}
					onIonChange={(e) =>
						setFilterDone(
							e.detail.value as "all" | "done" | "notDone"
						)
					}
				>
					<IonSegmentButton value="all">All</IonSegmentButton>
					<IonSegmentButton value="done">Completed</IonSegmentButton>
					<IonSegmentButton value="notDone">
						Not Completed
					</IonSegmentButton>
				</IonSegment>

				{/* Task List */}
				<IonList>
					{filteredTasks.map((task) => (
						<IonItem key={task.id}>
							<IonLabel>
								<h2>{task.title}</h2>
								<p>
									Created on:{" "}
									{new Date(task.date).toLocaleDateString()}
								</p>
							</IonLabel>

							{task.imageUrl && (
								<IonThumbnail slot="start">
									<IonImg src={task.imageUrl} />
								</IonThumbnail>
							)}

							{task.location && (
								<IonButton
									onClick={() => {
										setLoc(task.location);
										setShowMap(true);
									}}
								>
									<IonIcon icon={map}></IonIcon>
								</IonButton>
							)}
							<IonCheckbox
								slot="start"
								checked={task.done}
								onIonChange={() => handleToggleDone(task.id)}
							/>
							<IonButtons slot="end">
								<IonButton
									onClick={() => handleEditTask(task.id)}
								>
									<IonIcon icon={create} />
								</IonButton>
								<IonButton
									color="danger"
									onClick={() => handleDeleteTask(task.id)}
								>
									<IonIcon icon={trash} />
								</IonButton>
							</IonButtons>
						</IonItem>
					))}
				</IonList>

				<IonModal
					enterAnimation={enterAnimation}
					leaveAnimation={leaveAnimation}
					isOpen={showModal}
					onDidDismiss={() => setShowModal(false)}
				>
					<IonHeader>
						<IonToolbar>
							<IonTitle>
								{editTaskId ? "Edit Task" : "Add Task"}
							</IonTitle>
						</IonToolbar>
					</IonHeader>
					<IonContent>
						<IonInput
							label="Title"
							value={newTask.title}
							onIonInput={(e) =>
								setNewTask({
									...newTask,
									title: e.detail.value!,
								})
							}
						/>

						{/* Image upload section */}
						<IonButton onClick={captureImage}>
							<input
								type="file"
								accept="image/*"
								onChange={handleImageChange}
								hidden
							/>
							<IonIcon icon={camera} /> Upload Image
						</IonButton>
						{newTask.imageUrl && (
							<div>
								<img
									src={newTask.imageUrl}
									alt="Task Image"
									style={{
										width: "100%",
										marginTop: "10px",
									}}
								/>
							</div>
						)}

						<LocationSelector
							onLocationSelect={(lat, lng) =>
								setNewTask({
									...newTask,
									location: { lat, lng },
								})
							}
						/>
					</IonContent>
					<IonFooter>
						<IonToolbar>
							<IonButtons slot="end">
								<IonButton onClick={() => setShowModal(false)}>
									Cancel
								</IonButton>
								<IonButton onClick={handleSaveTask}>
									{editTaskId ? "Save Changes" : "Add Task"}
								</IonButton>
							</IonButtons>
						</IonToolbar>
					</IonFooter>
				</IonModal>

				<IonModal
					isOpen={showMap}
					onDidDismiss={() => {
						setShowMap(false);
					}}
				>
					<IonContent>
						<MapContainer
							center={loc}
							zoom={13}
							style={{ height: "600px", width: "100%" }}
						>
							<TileLayer
								url="https://tile.thunderforest.com/outdoors/{z}/{x}/{y}.png?apikey=189e2b815d834953b41e923a7c303ba0"
								attribution="&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
							/>
							<Marker position={loc}></Marker>
						</MapContainer>
					</IonContent>
				</IonModal>

				{/* Infinite Scroll */}
				<IonInfiniteScroll
					onIonInfinite={loadMoreTasks}
					disabled={visibleTasks.length >= tasks.length}
				>
					<IonInfiniteScrollContent loadingSpinner="dots" />
				</IonInfiniteScroll>
			</IonContent>

			{/* <IonToast
				isOpen={!!toastMessage}
				message={toastMessage}
				duration={2000}
				onDidDismiss={() => setToastMessage("")}
			/> */}
		</IonPage>
	);
};

export default ListPage;
