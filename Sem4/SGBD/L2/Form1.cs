using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Serialization;

namespace ProiectSGBD
{
    public partial class Form1 : Form
    {
        static string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
        
        static string parentName = ConfigurationManager.AppSettings["ParentTableName"];
        static string childName = ConfigurationManager.AppSettings["ChildTableName"];
        static int childNumberOfColumns = int.Parse(ConfigurationManager.AppSettings["ChildNumberOfColumns"]);
        static string insertQuerry = ConfigurationManager.AppSettings["ChildInsertQUERY"];
        static string deleteQuerry = ConfigurationManager.AppSettings["ChildDeleteQUERY"];
        static string updateQuerry = ConfigurationManager.AppSettings["ChildUpdateQUERY"];
       
        static string childArr = ConfigurationManager.AppSettings["ChildArr"];

        static string childColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
        static string childColumnTypes = ConfigurationManager.AppSettings["ChildColumnTypes"];
        static string childToParentID = ConfigurationManager.AppSettings["ChildToParentID"];



        SqlConnection connection = new SqlConnection(con);
        //SqlConnection connection = new SqlConnection("Data Source=DESKTOP-A96FI67\\SQLEXPRESS;Initial Catalog=MagazinElectronice;Integrated Security=True");

        SqlDataAdapter dataAdapter = new SqlDataAdapter();
        BindingSource bsP = new BindingSource();
        BindingSource bsC = new BindingSource();
        DataSet dataSetP = new DataSet();
        DataSet dataSetC = new DataSet();

        TextBox[] textBoxes = new TextBox[childNumberOfColumns];
        Label[] labels = new Label[childNumberOfColumns];
        public Form1() { 
            InitializeComponent();
            string[] names = childColumnNames.Split(','); ;

            dataGridView1.RowHeadersVisible = false;
            dataGridView2.RowHeadersVisible = false;

            for (int i = 0; i < childNumberOfColumns; i++)
            {
                labels[i] = new Label();
                textBoxes[i] = new TextBox();


                labels[i].Text = names[i];
                labels[i].Location = new Point(i * 150 + 200, 20);

                textBoxes[i].Text = "";
                textBoxes[i].Location = new Point(i * 150 + 200, 50);
            }
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            for (int i = 0; i < childNumberOfColumns; i++)
            {
                this.Controls.Add(labels[i]);
                this.Controls.Add(textBoxes[i]);
            }
        }
        private void butonConectare_Click(object sender, EventArgs e)
        {
            dataAdapter.SelectCommand = new SqlCommand("SELECT * FROM " + parentName, connection);
            dataSetP.Clear();
            dataAdapter.Fill(dataSetP);
            dataGridView1.DataSource = dataSetP.Tables[0];
            bsC.DataSource = dataSetP.Tables[0];
            bsP.MoveLast();

            butonConectare.Text = "Refresh";
        }

        private void butonAdaugare_Click(object sender, EventArgs e)
        {
            dataAdapter.InsertCommand = new SqlCommand(insertQuerry, connection);
            dataAdapter.InsertCommand.Parameters.Add("@id", SqlDbType.Int).Value = dataSetP.Tables[dataGridView1.CurrentCell.ColumnIndex].Rows[dataGridView1.CurrentCell.RowIndex][0];
            string[] args = childArr.Split(',');
            string[] types = childColumnTypes.Split(',');
            try
            {
                for (int i = 0; i < childNumberOfColumns; i++)
                {
                    switch (types[i])
                    {
                        case "string":
                            dataAdapter.InsertCommand.Parameters.Add(args[i + 1], SqlDbType.NVarChar).Value = textBoxes[i].Text;
                            break;
                        case "int":
                            dataAdapter.InsertCommand.Parameters.Add(args[i + 1], SqlDbType.Int).Value = int.Parse(textBoxes[i].Text);
                            break;
                        case "float":
                            dataAdapter.InsertCommand.Parameters.Add(args[i + 1], SqlDbType.Float).Value = float.Parse(textBoxes[i].Text);
                            break;
                        default:
                            MessageBox.Show("???");
                            break;
                    }
                }
                connection.Open();
                dataAdapter.InsertCommand.ExecuteNonQuery();
                connection.Close();
                dataSetC.Clear();
                dataAdapter.Fill(dataSetC);

            } catch (Exception ex) {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
            
        }
        private void butonDelete_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedCells.Count == 0)
            {
                MessageBox.Show("O linie in copil trebuie slectata!");
                return;
            }
            else if (dataGridView2.SelectedCells.Count > 1)
            {
                MessageBox.Show("O singura linie in copil trebuie slectata!");
                return;
            }

            dataAdapter.DeleteCommand = new SqlCommand(deleteQuerry, connection);

            dataAdapter.DeleteCommand.Parameters.Add("@id",
                SqlDbType.Int).Value = dataSetC.Tables[0].Rows[dataGridView2.CurrentCell.RowIndex][0];

            connection.Open();
            dataAdapter.DeleteCommand.ExecuteNonQuery();
            connection.Close();
            dataSetC.Clear();
            dataAdapter.Fill(dataSetC);
        }

        private void butonUpdate_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedCells.Count == 0)
            {
                MessageBox.Show("O linie in copil trebuie slectata!");
                return;
            }
            else if (dataGridView2.SelectedCells.Count > 1)
            {
                MessageBox.Show("O singura linie in copil trebuie slectata!");
                return;
            }

            int x;
            dataAdapter.UpdateCommand = new SqlCommand(updateQuerry, connection);

            dataAdapter.UpdateCommand.Parameters.Add("@id",
                SqlDbType.Int).Value = dataSetC.Tables[0].Rows[dataGridView2.CurrentCell.RowIndex][0];

            string[] args = childArr.Split(',');
            string[] types = childColumnTypes.Split(',');

            try
            {
                for (int i = 0; i < childNumberOfColumns; i++)
                {
                    switch (types[i])
                    {
                        case "string":
                            dataAdapter.UpdateCommand.Parameters.Add(args[i + 1], SqlDbType.VarChar).Value = textBoxes[i].Text;
                            break;
                        case "int":
                            dataAdapter.UpdateCommand.Parameters.Add(args[i + 1], SqlDbType.Int).Value = int.Parse(textBoxes[i].Text);
                            break;
                        case "float":
                            dataAdapter.UpdateCommand.Parameters.Add(args[i + 1], SqlDbType.Float).Value = float.Parse(textBoxes[i].Text);
                            break;
                    }
                }

                connection.Open();
                x = dataAdapter.UpdateCommand.ExecuteNonQuery();
                connection.Close();
                dataSetC.Clear();
                dataAdapter .Fill(dataSetC);

                if (x >= 1)
                    MessageBox.Show("The record has been updated");
            }
            catch
            {
                MessageBox.Show("Input gresit!");
                connection.Close(); ///////////////////////////////////////////////////////////////////////
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (dataGridView1.Rows[e.RowIndex].Cells[e.ColumnIndex].Value == null)
                    return;

                string Id_selectat = dataGridView1.Rows[e.RowIndex].Cells[0].Value.ToString();

                dataAdapter.SelectCommand = new SqlCommand("SELECT * from " + childName +
                        " where " + childName + "." + childToParentID + " = " + Id_selectat + "; ", connection);
                dataSetC.Clear();
                dataAdapter.Fill(dataSetC);
                dataGridView2.DataSource = dataSetC.Tables[0];
                bsC.DataSource = dataSetC.Tables[0];
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

    }
}
