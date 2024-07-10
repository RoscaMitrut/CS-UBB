namespace AgentieTurism
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
            dataCautareDatePicker = new DateTimePicker();
            obiectivCautareField = new TextBox();
            oraMinCautareField = new TextBox();
            oraMaxCautareField = new TextBox();
            numeClientRezervareField = new TextBox();
            numarTelefonRezervareField = new TextBox();
            numarLocuriRezervareField = new TextBox();
            cautareButon = new Button();
            excursiiView = new DataGridView();
            cautareView = new DataGridView();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            label5 = new Label();
            label6 = new Label();
            ((System.ComponentModel.ISupportInitialize)excursiiView).BeginInit();
            ((System.ComponentModel.ISupportInitialize)cautareView).BeginInit();
            SuspendLayout();
            // 
            // dataCautareDatePicker
            // 
            dataCautareDatePicker.Location = new Point(596, 54);
            dataCautareDatePicker.Name = "dataCautareDatePicker";
            dataCautareDatePicker.Size = new Size(200, 23);
            dataCautareDatePicker.TabIndex = 0;
            // 
            // obiectivCautareField
            // 
            obiectivCautareField.Location = new Point(643, 12);
            obiectivCautareField.Name = "obiectivCautareField";
            obiectivCautareField.Size = new Size(145, 23);
            obiectivCautareField.TabIndex = 1;
            // 
            // oraMinCautareField
            // 
            oraMinCautareField.Location = new Point(663, 97);
            oraMinCautareField.Name = "oraMinCautareField";
            oraMinCautareField.Size = new Size(100, 23);
            oraMinCautareField.TabIndex = 2;
            // 
            // oraMaxCautareField
            // 
            oraMaxCautareField.Location = new Point(637, 149);
            oraMaxCautareField.Name = "oraMaxCautareField";
            oraMaxCautareField.Size = new Size(140, 23);
            oraMaxCautareField.TabIndex = 3;
            // 
            // numeClientRezervareField
            // 
            numeClientRezervareField.Location = new Point(643, 269);
            numeClientRezervareField.Name = "numeClientRezervareField";
            numeClientRezervareField.Size = new Size(137, 23);
            numeClientRezervareField.TabIndex = 4;
            // 
            // numarTelefonRezervareField
            // 
            numarTelefonRezervareField.Location = new Point(637, 331);
            numarTelefonRezervareField.Name = "numarTelefonRezervareField";
            numarTelefonRezervareField.Size = new Size(151, 23);
            numarTelefonRezervareField.TabIndex = 5;
            // 
            // numarLocuriRezervareField
            // 
            numarLocuriRezervareField.Location = new Point(638, 384);
            numarLocuriRezervareField.Name = "numarLocuriRezervareField";
            numarLocuriRezervareField.Size = new Size(150, 23);
            numarLocuriRezervareField.TabIndex = 6;
            // 
            // cautareButon
            // 
            cautareButon.Location = new Point(643, 192);
            cautareButon.Name = "cautareButon";
            cautareButon.Size = new Size(98, 29);
            cautareButon.TabIndex = 7;
            cautareButon.Text = "Cauta";
            cautareButon.UseVisualStyleBackColor = true;
            cautareButon.Click += cautareButon_Click;
            // 
            // excursiiView
            // 
            excursiiView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            excursiiView.Location = new Point(0, 3);
            excursiiView.Name = "excursiiView";
            excursiiView.Size = new Size(532, 208);
            excursiiView.TabIndex = 8;
            excursiiView.CellClick += excursiiView_CellClick;
            excursiiView.RowPrePaint += excursiiView_RowPrePaint;
            // 
            // cautareView
            // 
            cautareView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            cautareView.Location = new Point(0, 217);
            cautareView.Name = "cautareView";
            cautareView.Size = new Size(533, 231);
            cautareView.TabIndex = 9;
            cautareView.CellClick += cautareView_CellClick;
            cautareView.RowPrePaint += cautareView_RowPrePaint;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(551, 13);
            label1.Name = "label1";
            label1.Size = new Size(51, 15);
            label1.TabIndex = 10;
            label1.Text = "Obiectiv";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(548, 83);
            label2.Name = "label2";
            label2.Size = new Size(50, 15);
            label2.TabIndex = 11;
            label2.Text = "Ora Min";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(545, 146);
            label3.Name = "label3";
            label3.Size = new Size(52, 15);
            label3.TabIndex = 12;
            label3.Text = "Ora Max";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(556, 265);
            label4.Name = "label4";
            label4.Size = new Size(40, 15);
            label4.TabIndex = 13;
            label4.Text = "Nume";
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(553, 325);
            label5.Name = "label5";
            label5.Size = new Size(85, 15);
            label5.TabIndex = 14;
            label5.Text = "Numar Telefon";
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(556, 377);
            label6.Name = "label6";
            label6.Size = new Size(80, 15);
            label6.TabIndex = 15;
            label6.Text = "Numar Locuri";
            // 
            // MainWindow
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(label6);
            Controls.Add(label5);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(cautareView);
            Controls.Add(excursiiView);
            Controls.Add(cautareButon);
            Controls.Add(numarLocuriRezervareField);
            Controls.Add(numarTelefonRezervareField);
            Controls.Add(numeClientRezervareField);
            Controls.Add(oraMaxCautareField);
            Controls.Add(oraMinCautareField);
            Controls.Add(obiectivCautareField);
            Controls.Add(dataCautareDatePicker);
            Name = "MainWindow";
            Text = "Main";
            ((System.ComponentModel.ISupportInitialize)excursiiView).EndInit();
            ((System.ComponentModel.ISupportInitialize)cautareView).EndInit();
            ResumeLayout(false);
            PerformLayout();
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