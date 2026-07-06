import cv2
import numpy as np
import tensorflow as tf
import time

model = tf.keras.models.load_model("../fire_detection_model.h5")

cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)

if not cap.isOpened():
    print("Could not open webcam")
    exit()

frame_drop_count = 0
max_frame_drops = 30
max_retries = 5

while True:
    ret = False
    retries = 0

    while not ret and retries < max_retries:
        ret, frame = cap.read()
        retries += 1
        if not ret:
            print(f"Frame grab failed, retrying... ({retries}/{max_retries})")
            time.sleep(0.1) 
    
    if not ret:
        frame_drop_count += 1
        print(f"Can't receive frame (stream end or error). Dropped frames: {frame_drop_count}/{max_frame_drops}")

        if frame_drop_count >= max_frame_drops:
            print(f"Exiting after {max_frame_drops} consecutive broken frames.")
            break
        continue
    frame_drop_count = 0


    if frame is None or frame.size == 0:
        print("Invalid frame received, skipping...")
        continue

    frame = cv2.flip(frame, 1)

    resized = cv2.resize(frame, (256, 256))
    normalized = resized / 255.0
    input_tensor = np.expand_dims(normalized, axis=0) 

    try:
        prediction = model.predict(input_tensor, verbose=0)[0][0]
    except Exception as e:
        print(f"Error during prediction: {e}")
        continue

    label = "Positive" if prediction >= 0.5 else "Negative"
    confidence = f"{prediction:.2f}"

    cv2.putText(frame, f"{label} ({confidence})", (10, 30),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)

    cv2.imshow("Webcam Classifier (press Q to quit)", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        print("Exiting webcam classifier")
        break

cap.release()
cv2.destroyAllWindows()
