using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Configuration;
using System.Diagnostics;

namespace BackspaceBoysUI
{
    public partial class Betting : System.Web.UI.Page
    {
        SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["NewBetConnectionString"].ConnectionString);
        SqlConnection strConn = new SqlConnection(ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString);

        string oldbalance = null;
        int balancenumber = 0;
        int newbalance = 0;
        string title = null;

        protected void Page_Load(object sender, EventArgs e)
        {
           
            con.Open();
 //           SqlConnection strConn = new SqlConnection(ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString);
            strConn.Open();
            if (User.Identity.IsAuthenticated)
            {
                Label3.Visible = true;
                SqlCommand command = new SqlCommand("SELECT SpaceBucks from AspNetUsers WHERE Email= '" + User.Identity.Name + "'", strConn);
                SqlDataReader read = command.ExecuteReader();
                if (read.Read())
                {
                    oldbalance = (read["SpaceBucks"].ToString());
                    Label3.Text = "Your balance: " + oldbalance + " Space Bucks";
                    read.Close();
                }
            }

            if (Session["infoObject"] != null)
            {
                MainPageFeedResults streamObject = (MainPageFeedResults)Session["infoObject"];
                if (streamObject != null)
                {

                    Debug.WriteLine(streamObject.matchTitle);
                    this.streamTitle.InnerText = streamObject.matchTitle;
                    title = streamObject.matchTitle;
                    string[] words = title.Split(new[] { " vs " }, StringSplitOptions.None);
                    this.team1button.Text = words[0];
                    this.team2button.Text = words[1];
                    this.gameInfoLink.HRef = streamObject.matchLink;
                    this.gameInfoLabel.InnerText = streamObject.matchTitle + " Inside Info and Tips provided by HLTV.com, click here";
                    this.SqlDataSource1.SelectCommand = "SELECT [User], [TeamChosen], [MatchName], [BetAmount] FROM [OpenBets] WHERE MatchName =" + "'" +title + "'";
                }
            }
            
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            string choice = null;
            if (!User.Identity.IsAuthenticated)
            {
                Label2.Visible = true;
                Label2.Text = "Please log in or register to submit a bet";
            }
            else if (team1button.Checked && team2button.Checked)
            {
                Label2.Visible = true;
                Label2.Text = "You may only select one team to bet on";
            }
            else if (!team1button.Checked && !team2button.Checked)
            {
                Label2.Visible = true;
                Label2.Text = "Please select a team to bet on";
            }
            else
            {
                if (Session["infoObject"] != null)
                {

                    MainPageFeedResults streamObject = (MainPageFeedResults)Session["infoObject"];

                    string[] words = streamObject.matchTitle.Split(' ');

                    if (team1button.Checked)
                    {
                        choice = words[0];
  //                      choice = "Team 1";
                    }
                    else if (team2button.Checked)
                    {
                        choice = words[2];
   //                     choice = "Team 2";
                    }
                }
                int number;
                bool result = Int32.TryParse(amountbox.Text, out number);
                if (result && number <= (Int32.Parse(oldbalance)) && number > 0)
                {


                    SqlCommand cmd = new SqlCommand("insert into OpenBets values('" + User.Identity.Name + "', '" + choice + "', '"+ title +"', '" + number + "')", con);

                    //               SqlCommand cmd = new SqlCommand("insert into TestTable values('" + User.Identity.Name + "', '" + choice + "', '" + number + "')", con);

                    cmd.ExecuteNonQuery();
                    //            SqlCommand cmd2 = new SqlCommand("delete from TestTable", con);
                    //            cmd2.ExecuteNonQuery();

                    //                    con.Close();
                    GridView1.DataBind();
                    Label2.Visible = true;
                    Label2.Text = "Your bet has been made " + User.Identity.Name;
                    SqlCommand updatecmd = new SqlCommand("UPDATE AspNetUsers SET SpaceBucks=@newSpaceBucks WHERE Email = '" + User.Identity.Name + "'", strConn);
                    //      SqlCommand updatecmd = new SqlCommand("UPDATE AspNetUsers SET SpaceBucks=SpaceBucks - '"+ Convert.ToInt32(amountbox.Text) +"' WHERE Email = '" + User.Identity.Name + "'", strConn);

                    //          updatecmd.Parameters.AddWithValue("@newSpaceBucks", (Convert.ToInt32(balance) - Convert.ToInt32(amountbox.Text)).ToString());
                    balancenumber = Convert.ToInt32(oldbalance);
                    newbalance = balancenumber - Convert.ToInt32(amountbox.Text);
                    //               newbalance = balancenumber - (Convert.ToInt32(amountbox.Text));

                    updatecmd.Parameters.AddWithValue("@newSpaceBucks", newbalance);
                    updatecmd.ExecuteNonQuery();
                    Label3.Text = "Your balance: " + newbalance + " Space Bucks";

                    //                 balance = newbalance.ToString(); 
                    //                  Label3.Text = "Your balance: " + newbalance.ToString() + " Space Bucks";

                    con.Close();
                }
                else
                {
                    if (!result)
                    {
                        Label2.Visible = true;
                        Label2.Text = "Invalid bet entry. Please enter a whole number";
                    }
                    else if (number > (Int32.Parse(oldbalance)))
                    {
                        Label2.Visible = true;
                        Label2.Text = "Insufficient balance to place your entered bet";
                    }
                    else if (number <= 0)
                    {
                        Label2.Visible = true;
                        Label2.Text = "Bet placed must be positive";
                    }
                }
                //         if(team1button.Checked == false && team2button.Checked = false)
            }
            //           if(team1button.Checked)
            //         {
            team1button.Checked = false;
            //         }
            //           else if(team2button.Checked)
            //           {
            team2button.Checked = false;
            //           }
            amountbox.Text = "";

            /* GridView1.DataSource = null;
               GridView1.DataBind();
            // GridView1.Columns.Clear(); */

        }
    }
}