using System.Drawing;
using System.Windows.Forms;

namespace client
{
    partial class MainWindow
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
            this.dataCautareDatePicker = new System.Windows.Forms.DateTimePicker();
            this.obiectivCautareField = new System.Windows.Forms.TextBox();
            this.oraMinCautareField = new System.Windows.Forms.TextBox();
            this.oraMaxCautareField = new System.Windows.Forms.TextBox();
            this.numeClientRezervareField = new System.Windows.Forms.TextBox();
            this.numarTelefonRezervareField = new System.Windows.Forms.TextBox();
            this.numarLocuriRezervareField = new System.Windows.Forms.TextBox();
            this.cautareButon = new System.Windows.Forms.Button();
            this.excursiiView = new System.Windows.Forms.DataGridView();
            this.cautareView = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.excursiiView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.cautareView)).BeginInit();
            this.SuspendLayout();
            // 
            // dataCautareDatePicker
            // 
            this.dataCautareDatePicker.Location = new System.Drawing.Point(511, 47);
            this.dataCautareDatePicker.Name = "dataCautareDatePicker";
            this.dataCautareDatePicker.Size = new System.Drawing.Size(172, 20);
            this.dataCautareDatePicker.TabIndex = 0;
            // 
            // obiectivCautareField
            // 
            this.obiectivCautareField.Location = new System.Drawing.Point(551, 10);
            this.obiectivCautareField.Name = "obiectivCautareField";
            this.obiectivCautareField.Size = new System.Drawing.Size(125, 20);
            this.obiectivCautareField.TabIndex = 1;
            // 
            // oraMinCautareField
            // 
            this.oraMinCautareField.Location = new System.Drawing.Point(568, 84);
            this.oraMinCautareField.Name = "oraMinCautareField";
            this.oraMinCautareField.Size = new System.Drawing.Size(86, 20);
            this.oraMinCautareField.TabIndex = 2;
            // 
            // oraMaxCautareField
            // 
            this.oraMaxCautareField.Location = new System.Drawing.Point(546, 129);
            this.oraMaxCautareField.Name = "oraMaxCautareField";
            this.oraMaxCautareField.Size = new System.Drawing.Size(121, 20);
            this.oraMaxCautareField.TabIndex = 3;
            // 
            // numeClientRezervareField
            // 
            this.numeClientRezervareField.Location = new System.Drawing.Point(551, 233);
            this.numeClientRezervareField.Name = "numeClientRezervareField";
            this.numeClientRezervareField.Size = new System.Drawing.Size(118, 20);
            this.numeClientRezervareField.TabIndex = 4;
            // 
            // numarTelefonRezervareField
            // 
            this.numarTelefonRezervareField.Location = new System.Drawing.Point(546, 287);
            this.numarTelefonRezervareField.Name = "numarTelefonRezervareField";
            this.numarTelefonRezervareField.Size = new System.Drawing.Size(130, 20);
            this.numarTelefonRezervareField.TabIndex = 5;
            // 
            // numarLocuriRezervareField
            // 
            this.numarLocuriRezervareField.Location = new System.Drawing.Point(547, 333);
            this.numarLocuriRezervareField.Name = "numarLocuriRezervareField";
            this.numarLocuriRezervareField.Size = new System.Drawing.Size(129, 20);
            this.numarLocuriRezervareField.TabIndex = 6;
            // 
            // cautareButon
            // 
            this.cautareButon.Location = new System.Drawing.Point(551, 166);
            this.cautareButon.Name = "cautareButon";
            this.cautareButon.Size = new System.Drawing.Size(84, 25);
            this.cautareButon.TabIndex = 7;
            this.cautareButon.Text = "Cauta";
            this.cautareButon.UseVisualStyleBackColor = true;
            this.cautareButon.Click += new System.EventHandler(this.cautareButon_Click);
            // 
            // excursiiView
            // 
            this.excursiiView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.excursiiView.Location = new System.Drawing.Point(0, 3);
            this.excursiiView.Name = "excursiiView";
            this.excursiiView.Size = new System.Drawing.Size(456, 180);
            this.excursiiView.TabIndex = 8;
            this.excursiiView.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.excursiiView_CellClick);
            // 
            // cautareView
            // 
            this.cautareView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.cautareView.Location = new System.Drawing.Point(0, 188);
            this.cautareView.Name = "cautareView";
            this.cautareView.Size = new System.Drawing.Size(457, 200);
            this.cautareView.TabIndex = 9;
            this.cautareView.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.cautareView_CellClick);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(472, 11);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(46, 13);
            this.label1.TabIndex = 10;
            this.label1.Text = "Obiectiv";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(470, 72);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(44, 13);
            this.label2.TabIndex = 11;
            this.label2.Text = "Ora Min";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(467, 127);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(47, 13);
            this.label3.TabIndex = 12;
            this.label3.Text = "Ora Max";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(477, 230);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(35, 13);
            this.label4.TabIndex = 13;
            this.label4.Text = "Nume";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(474, 282);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(77, 13);
            this.label5.TabIndex = 14;
            this.label5.Text = "Numar Telefon";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(477, 327);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(70, 13);
            this.label6.TabIndex = 15;
            this.label6.Text = "Numar Locuri";
            // 
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(686, 390);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.cautareView);
            this.Controls.Add(this.excursiiView);
            this.Controls.Add(this.cautareButon);
            this.Controls.Add(this.numarLocuriRezervareField);
            this.Controls.Add(this.numarTelefonRezervareField);
            this.Controls.Add(this.numeClientRezervareField);
            this.Controls.Add(this.oraMaxCautareField);
            this.Controls.Add(this.oraMinCautareField);
            this.Controls.Add(this.obiectivCautareField);
            this.Controls.Add(this.dataCautareDatePicker);
            this.Name = "MainWindow";
            this.Text = "Main";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MainWindow_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.excursiiView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.cautareView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private DateTimePicker dataCautareDatePicker;
        private TextBox obiectivCautareField;
        private TextBox oraMinCautareField;
        private TextBox oraMaxCautareField;
        private TextBox numeClientRezervareField;
        private TextBox numarTelefonRezervareField;
        private TextBox numarLocuriRezervareField;
        private Button cautareButon;
        private DataGridView excursiiView;
        private DataGridView cautareView;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private Label label5;
        private Label label6;
    }
}