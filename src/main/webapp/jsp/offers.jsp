<%@ page import="java.util.List, model.InternshipOffer" %>
<%
    List<InternshipOffer> offers = (List<InternshipOffer>) request.getAttribute("offers");
%>

<h2>Internship Offers</h2>
<a href="jsp/addOffer.jsp">Add New Offer</a>
<table border="1">
    <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    <%
        for(InternshipOffer offer : offers){
    %>
    <tr>
        <td><%= offer.getTitle() %></td>
        <td><%= offer.getDescription() %></td>
        <td>
            <a href="offer?action=edit&id=<%= offer.getIdOffer() %>">Edit</a>
            <a href="offer?action=delete&id=<%= offer.getIdOffer() %>">Delete</a>
        </td>
        <td>
            <form action="application" method="post">
                <input type="hidden" name="offerId" value="<%= offer.getIdOffer() %>">
                <button type="submit">Apply</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>