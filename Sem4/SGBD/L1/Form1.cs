using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Serialization;

namespace ProiectSGBD
{
    public partial class Form1 : Form
    {
        SqlConnection connection = new SqlConnection("Data Source=DESKTOP-A96FI67\\SQLEXPRESS;Initial Catalog=MagazinElectronice;Integrated Security=True");

        DataSet dataSet = new DataSet();

        SqlDataAdapter dataAdapter = new SqlDataAdapter();

        public Form1() { 
            InitializeComponent();
        }

        private void butonConectare_Click(object sender, EventArgs e)
        {
            String query = "Select * FROM Locatii";
            LoadDataIntoDataGridView(dataGridView1,query);

            butonConectare.Text = "Refresh";
        }

        private void butonAdaugare_Click(object sender, EventArgs e)
        {
            try
            {
                dataAdapter.InsertCommand = new SqlCommand("INSERT INTO Angajati (nume,functie,salar,id_l) VALUES (@n, @f, @s, @i)",connection);

                dataAdapter.InsertCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = textBoxNume.Text;
                dataAdapter.InsertCommand.Parameters.Add("@f",SqlDbType.VarChar).Value = textBoxFunctie.Text;
                dataAdapter.InsertCommand.Parameters.Add("@s",SqlDbType.Int).Value = Int32.Parse(textBoxSalar.Text);
                dataAdapter.InsertCommand.Parameters.Add("@i",SqlDbType.Int).Value = Int32.Parse(textBoxId_l.Text);

                connection.Open();
                dataAdapter.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Insert successful");
                connection.Close();

                String id_lSelectat = textBoxId_l.Text;
                String query = $"Select id_a,nume,functie,salar FROM Angajati Where id_l={id_lSelectat}";
                LoadDataIntoDataGridView(dataGridView2, query);

            } catch (Exception ex) {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
            
        }
        private void butonDelete_Click(object sender, EventArgs e)
        {   
            try
            {
                String ida = dataGridView2.SelectedRows[0].Cells[0].Value.ToString();

                dataAdapter.DeleteCommand = new SqlCommand("DELETE FROM Angajati WHERE id_a=@ida",connection);
                dataAdapter.DeleteCommand.Parameters.Add("@ida", SqlDbType.VarChar).Value = ida;
                connection.Open();
                dataAdapter.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Delete successful");
                connection.Close();

                String id_lSelectat = textBoxId_l.Text;
                String query = $"Select id_a,nume,functie,salar FROM Angajati Where id_l={id_lSelectat}";
                LoadDataIntoDataGridView(dataGridView2, query);
            }catch (Exception ex)
            {
                    MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void butonUpdate_Click(object sender, EventArgs e)
        {
            try
            {
                String ida = dataGridView2.SelectedRows[0].Cells[0].Value.ToString();
                dataAdapter.UpdateCommand = new SqlCommand("UPDATE Angajati SET nume=@n ,functie=@f ,salar=@s ,id_l=@i WHERE id_a=@ida", connection);
                dataAdapter.UpdateCommand.Parameters.Add("@n", SqlDbType.VarChar).Value = textBoxNume.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@f", SqlDbType.VarChar).Value = textBoxFunctie.Text;
                dataAdapter.UpdateCommand.Parameters.Add("@s", SqlDbType.Int).Value = Int32.Parse(textBoxSalar.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@i", SqlDbType.Int).Value = Int32.Parse(textBoxId_l.Text);
                dataAdapter.UpdateCommand.Parameters.Add("@ida", SqlDbType.VarChar).Value = ida;

                connection.Open();
                dataAdapter.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Update successful");
                connection.Close();

                String id_lSelectat = textBoxId_l.Text;
                String query = $"Select id_a,nume,functie,salar FROM Angajati Where id_l={id_lSelectat}";
                LoadDataIntoDataGridView(dataGridView2, query);
            }
            catch (Exception ex) {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try { 
                if (e.RowIndex >= 0)
                {
                    String id_lSelectat = dataGridView1.SelectedRows[0].Cells[0].Value.ToString();
                    textBoxId_l.Text = id_lSelectat;

                    String query = $"Select id_a,nume,functie,salar FROM Angajati Where id_l={id_lSelectat}";
                    LoadDataIntoDataGridView(dataGridView2,query);
                }
            }catch (Exception ex)
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
                    textBoxNume.Text = dataGridView2.SelectedRows[0].Cells[1].Value.ToString(); ;
                    textBoxFunctie.Text = dataGridView2.SelectedRows[0].Cells[2].Value.ToString(); ;
                    textBoxSalar.Text = dataGridView2.SelectedRows[0].Cells[3].Value.ToString(); ;
                       
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
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
            catch (Exception ex) {
                MessageBox.Show(ex.Message );
                connection.Close();
            }
        }


    }
}
