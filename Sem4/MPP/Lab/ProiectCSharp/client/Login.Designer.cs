using System.Drawing;
using System.Windows.Forms;

namespace client
{
    partial class Login
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }


        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            usernameBox = new TextBox();
            passwordBox = new TextBox();
            loginButton = new Button();
            SuspendLayout();
            // 
            // usernameBox
            // 
            usernameBox.Location = new Point(97, 56);
            usernameBox.Name = "usernameBox";
            usernameBox.Size = new Size(176, 23);
            usernameBox.TabIndex = 0;
            // 
            // passwordBox
            // 
            passwordBox.Location = new Point(97, 116);
            passwordBox.Name = "passwordBox";
            passwordBox.Size = new Size(176, 23);
            passwordBox.TabIndex = 1;
            // 
            // loginButton
            // 
            loginButton.Location = new Point(144, 190);
            loginButton.Name = "loginButton";
            loginButton.Size = new Size(75, 23);
            loginButton.TabIndex = 2;
            loginButton.Text = "Login";
            loginButton.UseVisualStyleBackColor = true;
            loginButton.Click += loginButton_Click;
            // 
            // Login
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(384, 272);
            Controls.Add(loginButton);
            Controls.Add(passwordBox);
            Controls.Add(usernameBox);
            Name = "Login";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox usernameBox;
        private TextBox passwordBox;
        private Button loginButton;
    }
}