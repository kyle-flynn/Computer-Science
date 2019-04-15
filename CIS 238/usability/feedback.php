
<?php
$mailto = "flynnky@mail.gvsu.edu,kautzc@gvsu.edu";//Enter your email address here, separated by commas. Replace student-name@mail.gvsu.edu placeholders with the actual email address. Be sure to include the instructor's email too.
$subject = "Usability Test";//Do not change anything else in this php file except for email addresses.
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
//Below loads the thankyou.html page after the form is submitted. Be sure it is located in the same directory (folder) as this script and the form webpage
   if (test){
        echo '<META HTTP-EQUIV="Refresh" Content="0; URL=thankyou.html">';
    }  else {
       return false;
    } 
?>