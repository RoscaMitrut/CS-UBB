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
            // butonAdaugare
            // 
            this.butonAdaugare.Location = new System.Drawing.Point(478, 87);
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
            this.dataGridView2.Location = new System.Drawing.Point(559, 87);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(446, 340);
            this.dataGridView2.TabIndex = 11;
            // 
            // butonUpdate
            // 
            this.butonUpdate.Location = new System.Drawing.Point(478, 133);
            this.butonUpdate.Name = "butonUpdate";
            this.butonUpdate.Size = new System.Drawing.Size(75, 23);
            this.butonUpdate.TabIndex = 12;
            this.butonUpdate.Text = "Update";
            this.butonUpdate.UseVisualStyleBackColor = true;
            this.butonUpdate.Click += new System.EventHandler(this.butonUpdate_Click);
            // 
            // butonDelete
            // 
            this.butonDelete.Location = new System.Drawing.Point(478, 177);
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
            this.ClientSize = new System.Drawing.Size(1028, 452);
            this.Controls.Add(this.butonDelete);
            this.Controls.Add(this.butonUpdate);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.butonAdaugare);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.butonConectare);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button butonConectare;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Button butonAdaugare;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.Button butonUpdate;
        private System.Windows.Forms.Button butonDelete;
    }
}

