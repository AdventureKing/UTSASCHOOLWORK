<%@ Page Title="Contact" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Contact.aspx.cs" Inherits="BackspaceBoysUI.Contact" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h1><strong><u><%: Title %></u></strong></h1>
    <h3>Questions? Contact us.</h3>
    <address>
        UTSA PROJECT<br />
        San Antonio, Tx 78249<br />
        <abbr title="Phone">P:</abbr>
        210-999-9999
    </address>

    <address>
        <strong>Support:</strong>   <a href="mailto:Support@example.com">Support@backspacebets.com</a><br />
        <strong>Marketing:</strong> <a href="mailto:Marketing@example.com">Marketing@backspacebets.com</a>
    </address>
    <table class="nav-justified">
        <tr>
            <td style="width: 316px; font-size: large"><strong><u>Team Member:</u></strong></td>
            <td style="font-size: large"><strong><u>E-mail:</u></strong></td>
        </tr>
        <tr>
            <td style="width: 316px">Victor Le</td>
            <td><a href="mailto:levictorv@gmail.com">levictorv@gmail.com</a></td>
        </tr>
        <tr>
            <td style="width: 316px">Ram Patel</td>
            <td>ram.patel7789@gmail.com</td>
        </tr>
        <tr>
            <td style="width: 316px">Brandon Snow</td>
            <td>basnow2010@gmail.com</td>
        </tr>
        <tr>
            <td style="width: 316px">Steven Stover</td>
            <td>sstorver9@gmail.com</td>
        </tr>
        <tr>
            <td style="width: 316px">Jason Wang</td>
            <td>wangmenghw@gmail.com</td>
        </tr>
    </table>
</asp:Content>
