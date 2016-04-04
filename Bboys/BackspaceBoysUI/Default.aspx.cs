using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml;
using System.Xml.Linq;
using System.Data.SqlClient;
using System.Configuration;


namespace BackspaceBoysUI
{
    public partial class _Default : Page
    {
        List<MainPageFeedResults> MatchfeedResults = new List<MainPageFeedResults>();
        List<MainPageFeedResults> NewsfeedResults = new List<MainPageFeedResults>();
        List<Button> matchButtonList = new List<Button>();
        protected void Page_Load(object sender, EventArgs e)
        {
            
            this.rssMatchFeedCall();

            this.updateMatchTable();

            this.rssNewsFeedCall();
            
            foreach(MainPageFeedResults match in MatchfeedResults)
            {
                Button tempButton = new Button();
                tempButton.Text = match.matchTitle;
                tempButton.Click += delegate
                {
                    //your code
                    Session["infoObject"] = match;
                    Response.Redirect("Betting.aspx");
                    
                };

                this.container1.Controls.Add(tempButton);
                this.container1.Controls.Add(new LiteralControl("<br /> <br />"));
                matchButtonList.Add(tempButton);
            }

            if (NewsfeedResults.Count >= 1)
            {
                this.newsList0.InnerText = NewsfeedResults.ElementAt(0).matchTitle;
                this.newslistLink0.HRef = NewsfeedResults.ElementAt(0).matchLink;
                if (NewsfeedResults.Count >= 1)
                {
                    this.newsList1.InnerText = NewsfeedResults.ElementAt(1).matchTitle;
                    this.newslistLink1.HRef = NewsfeedResults.ElementAt(1).matchLink;
                }
                if (NewsfeedResults.Count >= 2)
                {
                    this.newsList2.InnerText = NewsfeedResults.ElementAt(2).matchTitle;
                    this.newslistLink2.HRef = NewsfeedResults.ElementAt(2).matchLink;
                }
                if (NewsfeedResults.Count >= 3)
                {
                    this.newsList3.InnerText = NewsfeedResults.ElementAt(3).matchTitle;
                    this.newslistLink3.HRef = NewsfeedResults.ElementAt(3).matchLink;
                }
                if (NewsfeedResults.Count >= 4)
                {
                    this.newsList4.InnerText = NewsfeedResults.ElementAt(4).matchTitle;
                    this.newslistLink4.HRef = NewsfeedResults.ElementAt(4).matchLink;
                }
                if (NewsfeedResults.Count >= 5)
                {
                    this.newsList5.InnerText = NewsfeedResults.ElementAt(5).matchTitle;
                    this.newslistLink5.HRef = NewsfeedResults.ElementAt(5).matchLink;
                }
                if (NewsfeedResults.Count >= 6)
                {
                    this.newsList6.InnerText = NewsfeedResults.ElementAt(6).matchTitle;
                    this.newslistLink6.HRef = NewsfeedResults.ElementAt(6).matchLink;
                }
                if (NewsfeedResults.Count >= 7)
                {
                    this.newsList7.InnerText = NewsfeedResults.ElementAt(7).matchTitle;
                    this.newslistLink7.HRef = NewsfeedResults.ElementAt(7).matchLink;
                }
                if (NewsfeedResults.Count >= 8)
                {
                    this.newsList8.InnerText = NewsfeedResults.ElementAt(8).matchTitle;
                    this.newslistLink8.HRef = NewsfeedResults.ElementAt(8).matchLink;
                }
                if (NewsfeedResults.Count >= 9)
                {
                    this.newsList9.InnerText = NewsfeedResults.ElementAt(9).matchTitle;
                    this.newslistLink9.HRef = NewsfeedResults.ElementAt(9).matchLink;
                }

            }


        }
        protected void rssMatchFeedCall()
        {
            string url = "http://www.hltv.org/hltv.rss.php‎";


            XmlDocument MyRssDocument = new XmlDocument();
            MyRssDocument.Load(url);

            XmlNodeList MyRssList = MyRssDocument.SelectNodes("rss/channel/item");
            string sTitle = "";
            string sLink = "";
            string sDescription = "";
            string spubDate = "";
            string sgametype = "";

            for (int i = 0; i < MyRssList.Count; i++)
            {
                MainPageFeedResults f = new MainPageFeedResults();

                XmlNode MyRssDetail;

                MyRssDetail = MyRssList.Item(i).SelectSingleNode("title");
                if (MyRssDetail != null)
                    sTitle = MyRssDetail.InnerText;
                else
                    sTitle = "";

                MyRssDetail = MyRssList.Item(i).SelectSingleNode("link");
                if (MyRssDetail != null)
                    sLink = MyRssDetail.InnerText;
                else
                    sLink = "";

                MyRssDetail = MyRssList.Item(i).SelectSingleNode("description");
                if (MyRssDetail != null)
                    sDescription = MyRssDetail.InnerText;
                else
                {
                    sDescription = "";
                }
                MyRssDetail = MyRssList.Item(i).SelectSingleNode("pubDate");
                if (MyRssDetail != null)
                    spubDate = MyRssDetail.InnerText;
                else
                {
                    spubDate = "";
                }
                MyRssDetail = MyRssList.Item(i).SelectSingleNode("gametype");
                if (MyRssDetail != null)
                    sgametype = MyRssDetail.InnerText;
                else
                {
                    sgametype = "";
                }
                f.matchDes = sDescription;
                f.matchGameType = sgametype;
                f.matchPubDate = spubDate;
                f.matchTitle = sTitle;
                f.matchLink = sLink;
                MatchfeedResults.Add(f);

               // Debug.WriteLine(sTitle + "  " + sLink + "  " + sDescription + " " + sgametype + " " + spubDate);
            }
        }

        protected void rssNewsFeedCall()
        {
            string url = "http://www.hltv.org/news.rss.php‎";


            XmlDocument MyRssDocument = new XmlDocument();
            MyRssDocument.Load(url);

            XmlNodeList MyRssList = MyRssDocument.SelectNodes("rss/channel/item");
            string sTitle = "";
            string sLink = "";
           
            string spubDate = "";
            

            for (int i = 0; i < MyRssList.Count; i++)
            {
                MainPageFeedResults f = new MainPageFeedResults();

                XmlNode MyRssDetail;

                MyRssDetail = MyRssList.Item(i).SelectSingleNode("title");
                if (MyRssDetail != null)
                    sTitle = MyRssDetail.InnerText;
                else
                    sTitle = "";

                MyRssDetail = MyRssList.Item(i).SelectSingleNode("link");
                if (MyRssDetail != null)
                    sLink = MyRssDetail.InnerText;
                else
                    sLink = "";

                MyRssDetail = MyRssList.Item(i).SelectSingleNode("pubDate");
                if (MyRssDetail != null)
                    spubDate = MyRssDetail.InnerText;
                else
                {
                    spubDate = "";
                }
               
               
               
                f.matchPubDate = spubDate;
                f.matchTitle = sTitle;
                f.matchLink = sLink;
                NewsfeedResults.Add(f);

               Debug.WriteLine(sTitle + "  " + sLink + "  " + " " + spubDate);
            }
        }

        protected void updateMatchTable()
        {
            string matchName, team1, team2, time;
            matchName = team1 = team2 = time = null;   
            SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["NewBetConnectionString"].ConnectionString);
            con.Open();
            foreach (MainPageFeedResults match in MatchfeedResults)
            {

                matchName = match.matchTitle;
                string[] words = match.matchTitle.Split(new[] { " vs " }, StringSplitOptions.None);
                team1 = words[0];
                team2 = words[1];
                time = match.matchPubDate;
                SqlCommand cmd = new SqlCommand("IF NOT EXISTS(SELECT * FROM MatchTable WHERE MatchName = '" + matchName + "') INSERT INTO MatchTable(MatchName, Team1, Team2, Time) VALUES('" + matchName + "', '" + team1 + "', '" + team2 + "', '" + time + "')", con);

        //        SqlCommand cmd = new SqlCommand("INSERT INTO MatchTable values('"+ matchName +"', '"+ team1 +"', '"+ team2 +"', '"+ time +"') SELECT @par1, @par2, @par3 WHERE NOT EXISTS(SELECT   ", con);

         //       SqlCommand cmd = new SqlCommand("IF '" + matchName + "' NOT EXISTS(INSERT INTO MatchTable values('" + matchName + "', '" + team1 + "', '" + team2 + "', '" + time + "'))", con);
                cmd.ExecuteNonQuery();
            }

            con.Close();


        }

    }
}
