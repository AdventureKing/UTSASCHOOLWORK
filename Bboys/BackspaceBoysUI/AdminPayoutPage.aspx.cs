using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Configuration;
using System.Net;
using System.IO;
using Newtonsoft.Json.Linq;
using System.Diagnostics;

namespace BackspaceBoysUI
{
    public partial class AdminPayoutPage : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            HttpWebRequest request = WebRequest.Create("http://csgolounge.com/api/matches") as HttpWebRequest;
            StreamReader reader;
            String Topcontent = null;
            List<WinnerObject> listofwinners = new List<WinnerObject>();
            // Get response  
            using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
            {
                // Get the response stream  
                reader = new StreamReader(response.GetResponseStream());

                //save string response so we can cut it up
                Topcontent = reader.ReadToEnd();


            }

            //pull top out of string and turn into jobjects
            if (Topcontent != null)
            {
                var jsonData = JArray.Parse(Topcontent);
                


                foreach (JObject name in jsonData)
                {
                    WinnerObject tempObject = new WinnerObject();
                    //pull games array out and save in a secondary list
                    tempObject.teama = (name["a"].ToString());
                    tempObject.teamb = (name["b"].ToString());
                    tempObject.winner = (name["winner"].ToString());
                    tempObject.closed = (name["closed"].ToString());
                    tempObject.eventType = (name["event"].ToString());
                    tempObject.gameMatchtime = (name["when"].ToString());
                    if(tempObject.teama.Contains("'"))
                    {
                        String[] tempnamea = tempObject.teama.Split(new[] { "'" }, StringSplitOptions.None);
                                      tempObject.teama = tempnamea[0] + tempnamea[1];
 /*                       Debug.WriteLine(tempnamea);
                        foreach (String i in tempnamea)
                        {
                            Debug.WriteLine(i);
                            tempObject.teama = tempObject.teama + i;
                        }*/
                    }
                    if (tempObject.teamb.Contains("'"))
                    {
                        String[] tempnameb = tempObject.teamb.Split(new[] { "'" }, StringSplitOptions.None);
                        tempObject.teamb = tempnameb[0] + tempnameb[1];
        /*                foreach(String i in tempnameb)
                        {
                            Debug.WriteLine(i);
                            tempObject.teamb = tempObject.teamb + i;
                        }*/
                    }
                    tempObject.gameName = (tempObject.teama + " vs " + tempObject.teamb);

                    String[] tempDateChecker = tempObject.gameMatchtime.Split(new[] { " " }, StringSplitOptions.None);
                    //         Debug.WriteLine(tempDateChecker[0]);
                    String[] tempDayChecker = tempDateChecker[0].Split(new[] { "-" }, StringSplitOptions.None);
      //              Debug.WriteLine(tempDayChecker[2]);
                    int day = Convert.ToInt32(tempDayChecker[2]);
                    int month = Convert.ToInt32(tempDayChecker[1]);
                    int year = Convert.ToInt32(tempDayChecker[0]);

                    DateTime yesterday = DateTime.Now.AddDays(-1);
                    if(yesterday.Day == day && yesterday.Month == month && yesterday.Year == year)
                    {
                        listofwinners.Add(tempObject);
                    }


                }
                //test print
                 /*            foreach (WinnerObject topGame in listofwinners)
                             {
                                 Debug.WriteLine("GAME NAME: " + topGame.gameName + " Team A: " + topGame.teama + " Team B: " + topGame.teamb + " WINNER: " + topGame.winner + " TIME: " + topGame.gameMatchtime);
                             } */

                SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["NewBetConnectionString"].ConnectionString);
                con.Open();
                foreach (WinnerObject temp in listofwinners)
                {
                    //            SqlCommand cmd = new SqlCommand("IF NOT EXISTS(SELECT * FROM MatchTable WHERE MatchName = '" + matchName + "') INSERT INTO MatchTable(MatchName, Team1, Team2, Time) VALUES('" + matchName + "', '" + team1 + "', '" + team2 + "', '" + time + "')", con);
                    Debug.WriteLine(temp.gameMatchtime);
                    SqlCommand cmd = new SqlCommand("IF NOT EXISTS(SELECT * FROM WinnerReport WHERE MatchName = '" + temp.gameName + "') INSERT INTO WinnerReport(TeamA, TeamB, Winner, MatchName, Time) VALUES ('"+temp.teama+"', '"+temp.teamb+"', '"+temp.winner+"', '"+temp.gameName+"', '"+temp.gameMatchtime+"')", con);
                    cmd.ExecuteNonQuery();
                    //        SqlCommand cmd = new SqlCommand("IF NOT EXISTS(SELECT * FROM MatchTable WHERE MatchName = '" + matchName + "') INSERT INTO MatchTable(MatchName, Team1, Team2, Time) VALUES('" + matchName + "', '" + team1 + "', '" + team2 + "', '" + time + "')", con);
                }



                con.Close();
            }

        }
        public void ExecutePayOut(object sender, EventArgs e)
        {
            string matchName, winningTeam;
            string currentWinner, winnerAmount;
            string oldBalance;// newBalance;
            int oldBalanceNum, newBalanceNum;
            oldBalance = currentWinner = winnerAmount = null;
            oldBalanceNum = newBalanceNum = 0;
            matchName = nameOfMatch.Text;
            winningTeam = matchWinner.Text;
            string time = null;
            SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["NewBetConnectionString"].ConnectionString);
            SqlConnection defcon = new SqlConnection(ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString);

            SqlCommand matchdeletecmd = new SqlCommand("DELETE from MatchTable WHERE MatchName = '" + matchName + "'", con);
            SqlCommand opendeletecmd = new SqlCommand("DELETE from OpenBets WHERE MatchName = '" + matchName + "'", con);
            SqlCommand getWinnerscmd = new SqlCommand("SELECT * from OpenBets WHERE TeamChosen = '" + winningTeam + "' AND MatchName = '" + matchName + "'", con);
            SqlCommand updateClosecmd = new SqlCommand("INSERT INTO ClosedBets(TeamChosen, MatchName) VALUES('" + winningTeam + "', '" + matchName + "')", con);

            con.Open();
            defcon.Open();
//            getWinnerscmd.ExecuteNonQuery();
//            getWinnersBalance.ExecuteNonQuery();
            SqlDataReader read = getWinnerscmd.ExecuteReader();
            List<winnerObject> WinnerList = new List<winnerObject>();
 //           SqlCommand getWinnersBalance = new SqlCommand("SELECT SpaceBucks from AspNetUsers WHERE Email = '" + WinnerList.ElementAt() + "'", con);

//            SqlDataReader userread = getWinnersBalance.ExecuteReader();

            while (read.Read())
            {
                winnerObject tempObject = new winnerObject();

                tempObject.WinnerName = (read["User"].ToString());
                tempObject.WinnerAmount = (read["BetAmount"].ToString());
                WinnerList.Add(tempObject);
            }
            read.Close();
          
  //          SqlDataReader userread = getWinnersBalance.ExecuteReader();
            foreach (winnerObject temp in WinnerList)
            {
                int winnerAmountNum = Convert.ToInt32(temp.WinnerAmount) * 2;
                SqlCommand updatecmd = new SqlCommand("UPDATE AspNetUsers SET SpaceBucks = SpaceBucks + '"+ winnerAmountNum + "' WHERE email = '" + temp.WinnerName + "'", defcon);
                updatecmd.ExecuteNonQuery();
 //               SqlCommand updatecmd = new SqlCommand("UPDATE AspNetUsers SET SpaceBucks=@newSpaceBucks WHERE Email = '" + currentWinner + "'", defcon);
//                updatecmd.Parameters.AddWithValue("@newSpaceBucks", newBalanceNum);
            }
  //          userread.Close();
            
            matchdeletecmd.ExecuteNonQuery();
            opendeletecmd.ExecuteNonQuery();
            updateClosecmd.ExecuteNonQuery();
            con.Close();
            defcon.Close();
            Response.Redirect("~/AdminPayoutPage.aspx");
        }
    }
}