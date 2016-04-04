using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;


namespace BackspaceBoysUI
{
    public partial class PopularStreams : System.Web.UI.Page
    {
        List<TopStreamsResult> listofTopStreams = new List<TopStreamsResult>();
        

        protected void Page_Load(object sender, EventArgs e)
        {
            
            Debug.WriteLine("TEST");
            this.webserviceCall();
           
            for (int i = 0; i < 10; i++)
            {
                    if (i == 0)
                    {
                        this.player1.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                        this.streamTitle1.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                        }
                    else if (i == 1)
                    {
                        this.player2.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i+11).displayName + "/embed";
                         this.streamTitle2.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }
                    else if (i == 2)
                    {
                        this.player3.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle3.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }
                
                
                    else if (i == 3)
                    {
                        this.player4.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle4.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }
                    else if (i == 4)
                    {
                        this.player5.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle5.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }
                    else if (i == 5)
                    {
                        this.player6.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle6.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }


                else if (i == 6)
                {
                    this.player7.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle7.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }
                else if (i == 7)
                {
                    this.player8.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle8.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }
                else if (i == 8)
                {
                    this.player9.Attributes["src"] = "http://www.twitch.tv/" + listofTopStreams.ElementAt(i).displayName + "/embed";
                    this.streamTitle9.InnerText = listofTopStreams.ElementAt(i).displayName + " : " + listofTopStreams.ElementAt(i).streamStatus;
                }

            }

        }
        protected void webserviceCall()
        {
            HttpWebRequest request = WebRequest.Create("https://api.twitch.tv/kraken/streams") as HttpWebRequest;
            StreamReader reader;
            String Topcontent;

            // Get response  
            using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                // Get the response stream  
                reader = new StreamReader(response.GetResponseStream());

                //save string response so we can cut it up
                Topcontent = reader.ReadToEnd();


            }
       
            //pull top out of string and turn into jobjects
           var jsonData = JObject.Parse(Topcontent)["streams"];
         
            

            foreach (JObject name in jsonData)
            {
                TopStreamsResult tempObject = new TopStreamsResult();
                //pull games array out and save in a secondary list
                tempObject.gameName = (name["channel"]["game"].ToString());
                tempObject.displayName = (name["channel"]["display_name"].ToString());
                tempObject.streamStatus = (name["channel"]["status"].ToString());
                tempObject.streamlogo = (name["channel"]["profile_banner"].ToString());
                listofTopStreams.Add(tempObject);
                

            }
            //test print
            foreach (TopStreamsResult topGame in listofTopStreams)
            {
                Debug.WriteLine("DISPLAY NAME: " + topGame.displayName + " GAME NAME: " + topGame.gameName + " STREAM STATUS: " + topGame.streamStatus);
            }
          
            
        }
    }
}