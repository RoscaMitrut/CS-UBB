using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

using System.Data.SqlClient;
using System.Data;

namespace ProiectSGBD
{
    internal static class Program
    {
        [STAThread]
        static void Main()
        {
        //    SqlConnection connection = new SqlConnection("Data Source=DESKTOP-A96FI67\\SQLEXPRESS;Initial Catalog=MagazinElectronice;Integrated Security=True");
        //    SqlDataAdapter dataAdapter = new SqlDataAdapter();
        //    DataSet dataSet = new DataSet();
        //    BindingSource bindingSource = new BindingSource();

             /*
             ApplicationConfiguration.Initialize();
             Application.Run(new Form1());
        c     */

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
