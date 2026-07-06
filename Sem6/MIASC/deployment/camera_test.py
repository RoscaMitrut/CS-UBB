import cv2

camera_found = False
for index in range(3):
    cap = cv2.VideoCapture(index)  # 👈 Removed CAP_DSHOW
    if cap.isOpened():
        print(f"Camera found at index {index}")
        camera_found = True
        break
    cap.release()

if not camera_found:
    print("❌ Could not access any webcam. Check permissions or connect a camera.")
    exit()

while True:
    ret, frame = cap.read()
    if not ret:
        print("❌ Can't receive frame (stream end or error). Exiting ...")
        break

    frame = cv2.flip(frame, 1)
    cv2.imshow('📷 Webcam Feed (Press Q to Quit)', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        print("👋 Quitting webcam stream.")
        break

cap.release()
cv2.destroyAllWindows()
