using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ProjSGBD
{
    public partial class Form1 : Form
    {
        SqlConnection connection = new SqlConnection("Data Source=DESKTOP-A96FI67\\SQLEXPRESS;Initial Catalog=S12;Integrated Security=True");

        DataSet dataSet = new DataSet();

        SqlDataAdapter dataAdapter = new SqlDataAdapter();

        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String query = "Select * FROM TipuriSucuriNaturale";
            LoadDataIntoDataGridView(dataGridView1, query);

            button1.Text = "Refresh";
        }


        private void LoadDataIntoDataGridView(DataGridView dataGridView, string query)
        {
            try
            {
                SqlCommand command = new SqlCommand(query, connection);
                SqlDataAdapter adapter = new SqlDataAdapter(command);
                DataTable dataTable = new DataTable();
                connection.Open();
                adapter.Fill(dataTable);
                connection.Close();
                dataGridView.DataSource = dataTable;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void button2_Click(object sender, EventArgs e)//adaugare
        {
            try
            {
                dataAdapter.InsertCommand = new SqlCommand("INSERT INTO SucuriNaturale (Denumire,Producator,Gramaj,Pret,DataExpirare,TSid) VALUES (@d, @p, @g, @pr, @de, @i)", connection);

                dataAdapter.InsertCommand.Parameters.Add("@d", SqlDbType.VarChar).Value = textbox_denumire.Text;
                dataAdapter.InsertCommand.Parameters.Add("@p", SqlDbType.VarChar).Value = textbox_producator.Text;
                dataAdapter.InsertCommand.Parameters.Add("@g", SqlDbType.Int).Value = Int32.Parse(textbox_gramaj.Text);
                dataAdapter.InsertCommand.Parameters.Add("@pr", SqlDbType.Int).Value = Int32.Parse(textbox_pret.Text);
                dataAdapter.InsertCommand.Parameters.Add("@de", SqlDbType.Date).Value = DateTime.Parse(textbox_dataexpirare.Text);
                dataAdapter.InsertCommand.Parameters.Add("@i", SqlDbType.Int).Value = textbox_tsid.Text;

                connection.Open();
                dataAdapter.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Insert successful");
                connection.Close();

                String id_lSelectat = textbox_tsid.Text;
                String query = $"Select Sid,Denumire,Producator,Gramaj,Pret,DataExpirare,TSid FROM SucuriNaturale Where TSid={id_lSelectat}";
                LoadDataIntoDataGridView(dataGridView2, query);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void button3_Click(object sender, EventArgs e)//update
        {
            
            try
            {
                String ida = dataGridView2.SelectedRows[0].Cells[0].Value.ToString();
                dataAdapter.UpdateCommand = new SqlCommand("UPDATE SucuriNaturale SET TSid=@i ,Denumire=@d ,Producator=@p , Gramaj=@g, Pret=@pr, DataExpirare=@de WHERE Sid=@ii", connection);
                dataAdapter.UpdateCommand.Parameters.Add("@i", SqlDbType.Int).Value = Int32.Parse(textbox_tsid.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@d", SqlDbType.VarChar).Value = textbox_denumire.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@p", SqlDbType.VarChar).Value = textbox_producator.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@g", SqlDbType.Int).Value = Int32.Parse(textbox_gramaj.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@pr", SqlDbType.Int).Value = Int32.Parse(textbox_pret.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@de", SqlDbType.Date).Value = DateTime.Parse(textbox_dataexpirare.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@ii", SqlDbType.Int).Value = Int32.Parse(textbox_sid.Text);

                connection.Open();
                dataAdapter.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Update successful");
                connection.Close();

                String id_lSelectat = textbox_tsid.Text;
                String query = $"Select Sid,Denumire,Producator,Gramaj,Pret,DataExpirare,TSid FROM SucuriNaturale Where TSid={id_lSelectat}";
                LoadDataIntoDataGridView(dataGridView2, query);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
           
        }

        private void button4_Click(object sender, EventArgs e)//delete
        {
            try
            {
                String ida = dataGridView2.SelectedRows[0].Cells[0].Value.ToString();

                dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM SucuriNaturale WHERE Sid=@i", connection);
                dataAdapter.DeleteCommand.Parameters.Add("@i", SqlDbType.VarChar).Value = ida;
                connection.Open();
                dataAdapter.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Delete successful");
                connection.Close();

                String id_lSelectat = textbox_tsid.Text;
                String query = $"Select Sid,Denumire,Producator,Gramaj,Pret,DataExpirare,TSid FROM SucuriNaturale Where TSid={id_lSelectat}";
                LoadDataIntoDataGridView(dataGridView2, query);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (e.RowIndex >= 0)
                {
                    String id_lSelectat = dataGridView1.SelectedRows[0].Cells[0].Value.ToString();
                    //textBoxId_l.Text = id_lSelectat;

                    String query = $"Select Sid,Denumire,Producator,Gramaj,Pret,DataExpirare,TSid FROM SucuriNaturale Where TSid={id_lSelectat}";
                    LoadDataIntoDataGridView(dataGridView2, query);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void dataGridView2_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (e.RowIndex >= 0)
                {
                    textbox_sid.Text = dataGridView2.SelectedRows[0].Cells[0].Value.ToString(); ;
                    textbox_denumire.Text = dataGridView2.SelectedRows[0].Cells[1].Value.ToString(); ;
                    textbox_producator.Text = dataGridView2.SelectedRows[0].Cells[2].Value.ToString(); ;
                    textbox_gramaj.Text = dataGridView2.SelectedRows[0].Cells[3].Value.ToString(); ;
                    textbox_pret.Text = dataGridView2.SelectedRows[0].Cells[4].Value.ToString(); ;
                    textbox_dataexpirare.Text = dataGridView2.SelectedRows[0].Cells[5].Value.ToString(); ;
                    textbox_tsid.Text = dataGridView2.SelectedRows[0].Cells[6].Value.ToString(); ;

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }
    }
}
