import os
import sys
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import load_model
import cv2
import tkinter as tk
from tkinter import filedialog, messagebox, Label, Button, Frame
from PIL import Image, ImageTk

class FireDetectionApp:
    def __init__(self, master, model_path="fire_detection_model.h5"):
        self.master = master
        self.master.title("Fire Detection System")
        self.master.geometry("800x800")
        self.master.resizable(True, True)
        self.master.configure(bg="#f0f0f0")
        
        self.model_path = model_path
        self.model = None
        self.load_model()
        
        self.main_frame = Frame(self.master, bg="#f0f0f0")
        self.main_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
        
        self.title_label = Label(
            self.main_frame, 
            text="Fire Detection System", 
            font=("Arial", 24, "bold"),
            bg="#f0f0f0"
        )
        self.title_label.pack(pady=10)
        
        self.instruction_label = Label(
            self.main_frame,
            text="Upload an image to check for fire presence",
            font=("Arial", 14),
            bg="#f0f0f0"
        )
        self.instruction_label.pack(pady=10)
        
        self.upload_button = Button(
            self.main_frame,
            text="Upload Image",
            command=self.upload_image,
            font=("Arial", 12),
            bg="#4CAF50",
            fg="white",
            padx=20,
            pady=10
        )
        self.upload_button.pack(pady=20)
        
        self.image_frame = Frame(self.main_frame, bg="#f0f0f0")
        self.image_frame.pack(fill=tk.BOTH, expand=True)
        
        self.image_label = Label(self.image_frame, bg="#f0f0f0")
        self.image_label.pack(pady=10)
        
        self.result_label = Label(
            self.main_frame,
            text="",
            font=("Arial", 16, "bold"),
            bg="#f0f0f0"
        )
        self.result_label.pack(pady=10)
        
        self.current_image = None
        self.tk_image = None

    def load_model(self):
        try:
            self.model = load_model(self.model_path)
            print(f"Model loaded successfully from {self.model_path}")
        except Exception as e:
            print(f"Error loading model: {e}")
            messagebox.showerror("Model Error", f"Could not load model from {self.model_path}.\nError: {e}")
            self.master.quit()
    
    def upload_image(self):
        file_path = filedialog.askopenfilename(
            title="Select Image",
            filetypes=[
                ("Image files", "*.jpg *.jpeg *.png *.bmp *.gif"),
                ("All files", "*.*")
            ]
        )
        
        if file_path:
            try:
                self.process_image(file_path)
            except Exception as e:
                messagebox.showerror("Processing Error", f"Error processing image: {e}")
    
    def process_image(self, image_path):
        try:
            self.display_image(image_path)
            img = cv2.imread(image_path)
            if img is None:
                raise ValueError(f"Could not read image from {image_path}")
            img_resized = cv2.resize(img, (256, 256))
            
            img_rgb = cv2.cvtColor(img_resized, cv2.COLOR_BGR2RGB)
            
            img_normalized = img_rgb / 255.0
            
            img_batch = np.expand_dims(img_normalized, axis=0)
            
            prediction = self.model.predict(img_batch, verbose=0)[0][0]
            
            is_fire = prediction >= 0.5
            confidence = prediction * 100
            
            self.display_result(is_fire, confidence)
            
        except Exception as e:
            print(f"Error processing image: {e}")
            messagebox.showerror("Processing Error", f"Error processing image: {e}")
    
    def display_image(self, image_path):

        self.current_image = Image.open(image_path)
        
        width, height = self.current_image.size
        max_size = 500
        
        if width > height:
            new_width = min(width, max_size)
            new_height = int(new_width * height / width)
        else:
            new_height = min(height, max_size)
            new_width = int(new_height * width / height)
        
        resized_image = self.current_image.resize((new_width, new_height), Image.LANCZOS)
        
        self.tk_image = ImageTk.PhotoImage(resized_image)
        
        self.image_label.config(image=self.tk_image)
        self.image_label.image = self.tk_image  # Keep a reference
    
    def display_result(self, is_fire, confidence):
        result_text = "Fire Detected!" if is_fire else "No Fire Detected"
        confidence_text = f"Confidence: {confidence:.2f}%"
        
        result_color = "#FF3333" if is_fire else "#33CC33"
        
        self.result_label.config(
            text=f"{result_text}\n{confidence_text}",
            fg=result_color
        )

def main():
    model_path = "fire_detection_model.h5"
    if not os.path.exists(model_path):
        print(f"Error: Model file not found at {model_path}")
        messagebox.showerror("Model Error", f"Model file not found at {model_path}")
        return
    
    root = tk.Tk()
    app = FireDetectionApp(root, model_path)
    root.mainloop()

if __name__ == "__main__":
    main()