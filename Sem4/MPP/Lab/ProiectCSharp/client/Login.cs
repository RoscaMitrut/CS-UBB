using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using model;

namespace client
{
    public partial class Login : Form
    {
        private ProjectClientCtrl ctrl;
        public Login(ProjectClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username = usernameBox.Text;
            string password = passwordBox.Text;
            try{
                ctrl.login(username, password);
                MainWindow mainWin = new MainWindow(ctrl);
                mainWin.Text = "Window for " + username;
                mainWin.Show();
                this.Hide();
            }
            catch(Exception ex)
            {
                MessageBox.Show(this, "Login Error " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }
    }
}
