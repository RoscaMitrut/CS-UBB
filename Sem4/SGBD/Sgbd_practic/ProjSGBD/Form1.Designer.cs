namespace ProjSGBD
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
            this.button1 = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.textbox_sid = new System.Windows.Forms.TextBox();
            this.textbox_denumire = new System.Windows.Forms.TextBox();
            this.textbox_producator = new System.Windows.Forms.TextBox();
            this.textbox_gramaj = new System.Windows.Forms.TextBox();
            this.textbox_pret = new System.Windows.Forms.TextBox();
            this.textbox_dataexpirare = new System.Windows.Forms.TextBox();
            this.textbox_tsid = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(18, 17);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(103, 31);
            this.button1.TabIndex = 0;
            this.button1.Text = "Connect";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(18, 82);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(453, 332);
            this.dataGridView1.TabIndex = 1;
            this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(491, 82);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(658, 332);
            this.dataGridView2.TabIndex = 2;
            this.dataGridView2.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView2_CellClick);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(1180, 316);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 3;
            this.button2.Text = "Adaugare";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(1311, 316);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 4;
            this.button3.Text = "Update";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(1246, 370);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(75, 23);
            this.button4.TabIndex = 5;
            this.button4.Text = "Delete";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // textbox_sid
            // 
            this.textbox_sid.Location = new System.Drawing.Point(1221, 37);
            this.textbox_sid.Name = "textbox_sid";
            this.textbox_sid.Size = new System.Drawing.Size(100, 20);
            this.textbox_sid.TabIndex = 6;
            // 
            // textbox_denumire
            // 
            this.textbox_denumire.Location = new System.Drawing.Point(1221, 82);
            this.textbox_denumire.Name = "textbox_denumire";
            this.textbox_denumire.Size = new System.Drawing.Size(100, 20);
            this.textbox_denumire.TabIndex = 7;
            // 
            // textbox_producator
            // 
            this.textbox_producator.Location = new System.Drawing.Point(1221, 120);
            this.textbox_producator.Name = "textbox_producator";
            this.textbox_producator.Size = new System.Drawing.Size(100, 20);
            this.textbox_producator.TabIndex = 8;
            // 
            // textbox_gramaj
            // 
            this.textbox_gramaj.Location = new System.Drawing.Point(1221, 166);
            this.textbox_gramaj.Name = "textbox_gramaj";
            this.textbox_gramaj.Size = new System.Drawing.Size(100, 20);
            this.textbox_gramaj.TabIndex = 9;
            // 
            // textbox_pret
            // 
            this.textbox_pret.Location = new System.Drawing.Point(1221, 212);
            this.textbox_pret.Name = "textbox_pret";
            this.textbox_pret.Size = new System.Drawing.Size(100, 20);
            this.textbox_pret.TabIndex = 10;
            // 
            // textbox_dataexpirare
            // 
            this.textbox_dataexpirare.Location = new System.Drawing.Point(1221, 255);
            this.textbox_dataexpirare.Name = "textbox_dataexpirare";
            this.textbox_dataexpirare.Size = new System.Drawing.Size(100, 20);
            this.textbox_dataexpirare.TabIndex = 11;
            // 
            // textbox_tsid
            // 
            this.textbox_tsid.Location = new System.Drawing.Point(1221, 281);
            this.textbox_tsid.Name = "textbox_tsid";
            this.textbox_tsid.Size = new System.Drawing.Size(100, 20);
            this.textbox_tsid.TabIndex = 12;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1408, 450);
            this.Controls.Add(this.textbox_tsid);
            this.Controls.Add(this.textbox_dataexpirare);
            this.Controls.Add(this.textbox_pret);
            this.Controls.Add(this.textbox_gramaj);
            this.Controls.Add(this.textbox_producator);
            this.Controls.Add(this.textbox_denumire);
            this.Controls.Add(this.textbox_sid);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.button1);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.TextBox textbox_sid;
        private System.Windows.Forms.TextBox textbox_denumire;
        private System.Windows.Forms.TextBox textbox_producator;
        private System.Windows.Forms.TextBox textbox_gramaj;
        private System.Windows.Forms.TextBox textbox_pret;
        private System.Windows.Forms.TextBox textbox_dataexpirare;
        private System.Windows.Forms.TextBox textbox_tsid;
    }
}

