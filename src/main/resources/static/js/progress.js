
function alerts(like,dislike,id){
 var wid = (like /(like+dislike))*200;
if(like!=0 || dislike!=0){
    
    if(like==0){
     document.getElementsByClassName("progress")[id].style.width = 1+ "px";
        document.getElementsByClassName("progressLabel")[id].innerHTML= 0 + "% ";
    }else{
    document.getElementsByClassName("progress")[id].style.width = wid + "px";
    document.getElementsByClassName("progressLabel")[id].innerHTML= wid/2 + "% ";
}
}else{
    document.getElementsByClassName("progress")[id].style.width = 1+ "px";
    document.getElementsByClassName("progressLabel")[id].innerHTML= 0 + "%";
}

}
alerts(like,dislike,id)