<%@ Page Title="Admin Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="AdminPayoutPage.aspx.cs" Inherits="BackspaceBoysUI.AdminPayoutPage" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">

    <div class="jumbotron">
        <h1 style="text-align: center"><strong>Admin BackSpace Bets</strong></h1>
        <h3 style="text-align: center">Welcome to the Admin Page!</h3>
    </div>

    <hr />
   
    <div class="row">
        <center>
            Name of Match In Database Table (MatchTable):
            <asp:TextBox ID="nameOfMatch"  runat="server" />
            <br /><br />
            The winner of the match (FullTeamName):
            <asp:TextBox ID="matchWinner" Text="" runat="server" />
            <br /><br />
        </center>

        <center>
            <strong>
                <asp:Button id="payoutButton" Font-Bold="True" Text="Execute PayOut to Winners" OnClick="ExecutePayOut" runat="server" Height="46px" Width="257px"/>
            </strong>
        </center>
    </div>

    <br />
    <hr />

    <div class="row">
        <div class="col-md-5">
            <h3 style="text-align: center"><strong>Closed Bets</strong></h3>
            <hr />
            <center><asp:GridView ID="GridView1" runat="server" AllowPaging="True" AllowSorting="True" AutoGenerateColumns="False" DataKeyNames="Id" DataSourceID="SqlDataSource1">
            <Columns>
                <asp:BoundField DataField="Id" HeaderText="Id" ReadOnly="True" SortExpression="Id" InsertVisible="False" />
                <asp:BoundField DataField="TeamChosen" HeaderText="TeamChosen" SortExpression="TeamChosen" />
                <asp:BoundField DataField="MatchName" HeaderText="MatchName" SortExpression="MatchName" />
                <asp:BoundField DataField="Time" HeaderText="Time" SortExpression="Time" />
            </Columns>
            </asp:GridView></center>
            <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT * FROM [ClosedBets] ORDER BY [Id] DESC"></asp:SqlDataSource>
            <asp:SqlDataSource ID="SqlDataSource2" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT * FROM [MatchTable] ORDER BY [Id] DESC"></asp:SqlDataSource>
            <asp:SqlDataSource ID="SqlDataSource3" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT * FROM [OpenBets] ORDER BY [Id] DESC"></asp:SqlDataSource>
        </div>
        <div class="col-md-7">
            <h3 style="text-align: center"><strong>Match Table</strong></h3>
            <hr />
            <asp:GridView ID="GridView2" runat="server" AllowPaging="True" AllowSorting="True" AutoGenerateColumns="False" DataKeyNames="Id" DataSourceID="SqlDataSource2">
            <Columns>
                <asp:BoundField DataField="Id" HeaderText="Id" ReadOnly="True" SortExpression="Id" InsertVisible="False" />
                <asp:BoundField DataField="MatchName" HeaderText="MatchName" SortExpression="MatchName" />
                <asp:BoundField DataField="Team1" HeaderText="Team1" SortExpression="Team1" />
                <asp:BoundField DataField="Team2" HeaderText="Team2" SortExpression="Team2" />
                <asp:BoundField DataField="Time" HeaderText="Time" SortExpression="Time" />
            </Columns>
            </asp:GridView>
        </div>
    </div>

    <br /><br />
    <hr />
    <br />

    <div class="row">
        <div class="col-md-6">
            <h3 style="text-align: center"><strong>Open Bets</strong></h3>
            <hr />
            <center><asp:GridView ID="GridView3" runat="server" AllowPaging="True" AllowSorting="True" AutoGenerateColumns="False" DataKeyNames="Id" DataSourceID="SqlDataSource3">
            <Columns>
                <asp:BoundField DataField="Id" HeaderText="Id" ReadOnly="True" SortExpression="Id" InsertVisible="False" />
                <asp:BoundField DataField="User" HeaderText="User" SortExpression="User" />
                <asp:BoundField DataField="TeamChosen" HeaderText="TeamChosen" SortExpression="TeamChosen" />
                <asp:BoundField DataField="MatchName" HeaderText="MatchName" SortExpression="MatchName" />
                <asp:BoundField DataField="BetAmount" HeaderText="BetAmount" SortExpression="BetAmount" />
            </Columns>
            </asp:GridView></center>
        </div>

        <div class="col-md-6">
            <h3 style="text-align: center"><strong>Winner Table</strong></h3>
            <hr />
            <asp:SqlDataSource ID="SqlDataSource4" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT [TeamA], [TeamB], [Winner], [MatchName], [Time] FROM [WinnerReport] ORDER BY [Id] DESC"></asp:SqlDataSource>
            <center><asp:GridView ID="GridView4" runat="server" AllowPaging="True" AutoGenerateColumns="False" DataSourceID="SqlDataSource4">
            <Columns>
                <asp:BoundField DataField="TeamA" HeaderText="TeamA" SortExpression="TeamA" />
                <asp:BoundField DataField="TeamB" HeaderText="TeamB" SortExpression="TeamB" />
                <asp:BoundField DataField="Winner" HeaderText="Winner" SortExpression="Winner" />
                <asp:BoundField DataField="MatchName" HeaderText="MatchName" SortExpression="MatchName" />
                <asp:BoundField DataField="Time" HeaderText="Time" SortExpression="Time" />
            </Columns>
            </asp:GridView></center>
        </div>
    </div>


</asp:Content>
