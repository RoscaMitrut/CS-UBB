using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AgentieTurism.domain;
using AgentieTurism.repo;
using AgentieTurism.service;

namespace AgentieTurism
{
    public partial class Login : Form
    {
        private Service service;
        public Login()
        {
            InitializeComponent();
        }

        public void SetService(Service service)
        {
            this.service = service;
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username = usernameBox.Text;
            string password = passwordBox.Text;
            User user = service.getUser(username, password);
            if (user.id != 0)
            {
                MainWindow main = new MainWindow();
                main.SetService(service);
                main.Show(this);
                usernameBox.Clear();
                passwordBox.Clear();

            }
            else
            {
                MessageBox.Show("Invalid Credentials!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);

            }

        }
    }
}
