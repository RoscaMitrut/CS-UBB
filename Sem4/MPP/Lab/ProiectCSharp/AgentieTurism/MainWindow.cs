using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.VisualStyles;
using AgentieTurism.domain;
using AgentieTurism.service;

namespace AgentieTurism
{
    public partial class MainWindow : Form
    {
        private Service service;
        public MainWindow()
        {
            InitializeComponent();
        }

        public void SetService(Service service)
        {
            this.service = service;
            excursiiView.DataSource = service.getExcursii();
        }

        private void populateExcursiiView()
        {
            excursiiView.Rows.Clear();
            excursiiView.Refresh();
            excursiiView.DataSource = service.getExcursii();

        }

        private void cautareButon_Click(object sender, EventArgs e)
        {
            try
            {
                String obiectiv = obiectivCautareField.Text;
                DateTime data = dataCautareDatePicker.Value.Date;
                int oraMin = Int32.Parse(oraMinCautareField.Text);
                int oraMax = Int32.Parse(oraMaxCautareField.Text);

                cautareView.DataSource = null;
                cautareView.Rows.Clear();
                cautareView.DataSource = service.getExcursiiLaLocSiOra(obiectiv, data, oraMin, oraMax);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Introduceti date valide");
            }
        }

        private void excursiiView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            DataGridViewRow data = excursiiView.Rows[0];
            int id = (int)data.Cells["id"].Value;
            Excursie excursie = service.GetExcursie(id);
            String numeClient;
            int nrTelefon;
            int nrLocuri;
            if (excursie.id != 0)
            {
                try
                {
                    numeClient = numeClientRezervareField.Text;
                    nrTelefon = Int32.Parse(numarTelefonRezervareField.Text);
                    nrLocuri = Int32.Parse(numarLocuriRezervareField.Text);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Introduceti date valide");
                    return;
                }
                DialogResult dialogResult = MessageBox.Show("Are you sure?", "Confirm", MessageBoxButtons.YesNo);
                if (dialogResult == DialogResult.Yes)
                {
                    Boolean switcher = service.rezerva(excursie, numeClient, nrTelefon, nrLocuri);
                    if (switcher)
                    {
                        excursiiView.DataSource = null;
                        excursiiView.Rows.Clear();
                        excursiiView.DataSource = service.getExcursii();
                        cautareView.DataSource = null;
                        cautareView.Rows.Clear();
                    }
                    else
                    {
                        MessageBox.Show("Nu exista destule locuri disponibile");
                    }
                }
                else
                {

                }

            }
        }

        private void cautareView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            DataGridViewRow data = cautareView.Rows[0];
            int id = (int)data.Cells["id"].Value;
            Excursie excursie = service.GetExcursie(id);
            String numeClient;
            int nrTelefon;
            int nrLocuri;
            if (excursie.id != 0)
            {
                try
                {
                    numeClient = numeClientRezervareField.Text;
                    nrTelefon = Int32.Parse(numarTelefonRezervareField.Text);
                    nrLocuri = Int32.Parse(numarLocuriRezervareField.Text);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Introduceti date valide");
                    return;
                }
                DialogResult dialogResult = MessageBox.Show("Sure", "Confirm?", MessageBoxButtons.YesNo);
                if (dialogResult == DialogResult.Yes)
                {
                    Boolean switcher = service.rezerva(excursie, numeClient, nrTelefon, nrLocuri);
                    if (switcher)
                    {
                        excursiiView.DataSource = null;
                        excursiiView.Rows.Clear();
                        excursiiView.DataSource = service.getExcursii();
                        cautareView.DataSource = null;
                        cautareView.Rows.Clear();
                    }
                    else
                    {
                        MessageBox.Show("Nu exista destule locuri disponibile");
                    }
                }
                else
                {

                }

            }

        }

        private void excursiiView_RowPrePaint(object sender, DataGridViewRowPrePaintEventArgs e)
        {
            DataGridViewRow row = excursiiView.Rows[e.RowIndex];
            if (Convert.ToInt32(row.Cells[4].Value) == 0)
            {
                row.DefaultCellStyle.BackColor = Color.Red;
            }
            else
            {
                row.DefaultCellStyle.BackColor = Color.White;
            }

        }

        private void cautareView_RowPrePaint(object sender, DataGridViewRowPrePaintEventArgs e)
        {
            DataGridViewRow row = cautareView.Rows[e.RowIndex];
            if (Convert.ToInt32(row.Cells[4].Value) == 0)
            {
                row.DefaultCellStyle.BackColor = Color.Red;
            }
            else
            {
                row.DefaultCellStyle.BackColor = Color.White;
            }
        }
    }
}
