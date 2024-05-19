namespace ProiectSGBD
{
    partial class Form1
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
            this.butonConectare = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.labelNume = new System.Windows.Forms.Label();
            this.labelFunctie = new System.Windows.Forms.Label();
            this.labelSalar = new System.Windows.Forms.Label();
            this.labelId_l = new System.Windows.Forms.Label();
            this.textBoxNume = new System.Windows.Forms.TextBox();
            this.textBoxFunctie = new System.Windows.Forms.TextBox();
            this.textBoxSalar = new System.Windows.Forms.TextBox();
            this.textBoxId_l = new System.Windows.Forms.TextBox();
            this.butonAdaugare = new System.Windows.Forms.Button();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.butonUpdate = new System.Windows.Forms.Button();
            this.butonDelete = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // butonConectare
            // 
            this.butonConectare.Location = new System.Drawing.Point(26, 25);
            this.butonConectare.Name = "butonConectare";
            this.butonConectare.Size = new System.Drawing.Size(101, 31);
            this.butonConectare.TabIndex = 0;
            this.butonConectare.Text = "Connect";
            this.butonConectare.UseVisualStyleBackColor = true;
            this.butonConectare.Click += new System.EventHandler(this.butonConectare_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(27, 87);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(445, 340);
            this.dataGridView1.TabIndex = 1;
            this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            // 
            // labelNume
            // 
            this.labelNume.AutoSize = true;
            this.labelNume.Location = new System.Drawing.Point(978, 65);
            this.labelNume.Name = "labelNume";
            this.labelNume.Size = new System.Drawing.Size(35, 13);
            this.labelNume.TabIndex = 2;
            this.labelNume.Text = "Nume";
            // 
            // labelFunctie
            // 
            this.labelFunctie.AutoSize = true;
            this.labelFunctie.Location = new System.Drawing.Point(978, 135);
            this.labelFunctie.Name = "labelFunctie";
            this.labelFunctie.Size = new System.Drawing.Size(42, 13);
            this.labelFunctie.TabIndex = 3;
            this.labelFunctie.Text = "Functie";
            // 
            // labelSalar
            // 
            this.labelSalar.AutoSize = true;
            this.labelSalar.Location = new System.Drawing.Point(978, 207);
            this.labelSalar.Name = "labelSalar";
            this.labelSalar.Size = new System.Drawing.Size(31, 13);
            this.labelSalar.TabIndex = 4;
            this.labelSalar.Text = "Salar";
            // 
            // labelId_l
            // 
            this.labelId_l.AutoSize = true;
            this.labelId_l.Location = new System.Drawing.Point(978, 275);
            this.labelId_l.Name = "labelId_l";
            this.labelId_l.Size = new System.Drawing.Size(54, 13);
            this.labelId_l.TabIndex = 5;
            this.labelId_l.Text = "Id Locatie";
            // 
            // textBoxNume
            // 
            this.textBoxNume.Location = new System.Drawing.Point(1047, 65);
            this.textBoxNume.Name = "textBoxNume";
            this.textBoxNume.Size = new System.Drawing.Size(138, 20);
            this.textBoxNume.TabIndex = 6;
            // 
            // textBoxFunctie
            // 
            this.textBoxFunctie.Location = new System.Drawing.Point(1047, 132);
            this.textBoxFunctie.Name = "textBoxFunctie";
            this.textBoxFunctie.Size = new System.Drawing.Size(138, 20);
            this.textBoxFunctie.TabIndex = 7;
            // 
            // textBoxSalar
            // 
            this.textBoxSalar.Location = new System.Drawing.Point(1047, 207);
            this.textBoxSalar.Name = "textBoxSalar";
            this.textBoxSalar.Size = new System.Drawing.Size(138, 20);
            this.textBoxSalar.TabIndex = 8;
            // 
            // textBoxId_l
            // 
            this.textBoxId_l.Location = new System.Drawing.Point(1047, 275);
            this.textBoxId_l.Name = "textBoxId_l";
            this.textBoxId_l.Size = new System.Drawing.Size(138, 20);
            this.textBoxId_l.TabIndex = 9;
            // 
            // butonAdaugare
            // 
            this.butonAdaugare.Location = new System.Drawing.Point(1110, 330);
            this.butonAdaugare.Name = "butonAdaugare";
            this.butonAdaugare.Size = new System.Drawing.Size(75, 23);
            this.butonAdaugare.TabIndex = 10;
            this.butonAdaugare.Text = "Adaugare";
            this.butonAdaugare.UseVisualStyleBackColor = true;
            this.butonAdaugare.Click += new System.EventHandler(this.butonAdaugare_Click);
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(497, 87);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(446, 340);
            this.dataGridView2.TabIndex = 11;
            this.dataGridView2.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellClick);
            // 
            // butonUpdate
            // 
            this.butonUpdate.Location = new System.Drawing.Point(981, 330);
            this.butonUpdate.Name = "butonUpdate";
            this.butonUpdate.Size = new System.Drawing.Size(75, 23);
            this.butonUpdate.TabIndex = 12;
            this.butonUpdate.Text = "Update";
            this.butonUpdate.UseVisualStyleBackColor = true;
            this.butonUpdate.Click += new System.EventHandler(this.butonUpdate_Click);
            // 
            // butonDelete
            // 
            this.butonDelete.Location = new System.Drawing.Point(1047, 382);
            this.butonDelete.Name = "butonDelete";
            this.butonDelete.Size = new System.Drawing.Size(75, 23);
            this.butonDelete.TabIndex = 13;
            this.butonDelete.Text = "Delete";
            this.butonDelete.UseVisualStyleBackColor = true;
            this.butonDelete.Click += new System.EventHandler(this.butonDelete_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1224, 452);
            this.Controls.Add(this.butonDelete);
            this.Controls.Add(this.butonUpdate);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.butonAdaugare);
            this.Controls.Add(this.textBoxId_l);
            this.Controls.Add(this.textBoxSalar);
            this.Controls.Add(this.textBoxFunctie);
            this.Controls.Add(this.textBoxNume);
            this.Controls.Add(this.labelId_l);
            this.Controls.Add(this.labelSalar);
            this.Controls.Add(this.labelFunctie);
            this.Controls.Add(this.labelNume);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.butonConectare);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button butonConectare;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Label labelNume;
        private System.Windows.Forms.Label labelFunctie;
        private System.Windows.Forms.Label labelSalar;
        private System.Windows.Forms.Label labelId_l;
        private System.Windows.Forms.TextBox textBoxNume;
        private System.Windows.Forms.TextBox textBoxFunctie;
        private System.Windows.Forms.TextBox textBoxSalar;
        private System.Windows.Forms.TextBox textBoxId_l;
        private System.Windows.Forms.Button butonAdaugare;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.Button butonUpdate;
        private System.Windows.Forms.Button butonDelete;
    }
}

