
<?php
$mailto = "flynnky@mail.gvsu.edu";//Enter YOUR email address here surrounded by the quotes. Before testing the form on the EOS machines, be sure the permissions for this file are set (page 18 of the Setup Website Hosting instructions). Also, if you do not immediately receive an email, check your junk folder. Do not change anything else in this php file except for your email address. Doing so can cause problems on the server and Lawrence, our server administrator, will not be a happy camper.
$subject = "Contact Form"; 
$header = "From: ".$_POST['email'];
foreach ($_POST as $key => $value)
{
   if (!is_array($value))
   {
      $message .= "\n".$key." : ".$value;
   }
   else
   {
      foreach ($_POST[$key] as $itemvalue)
      {
         $message .= "\n".$key." : ".$itemvalue;
      }
   }
}
mail($mailto, $subject, stripslashes($message), $header);
//Below loads the thankyou.html page after the form is submitted. You have to create the thankyou.html page and be sure it is located in the same directory (folder) as this script and the contact.html webpage
   if (test){
        echo '<META HTTP-EQUIV="Refresh" Content="0; URL=thankyou.html">';
    }  else {
       return false;
    } 
?>