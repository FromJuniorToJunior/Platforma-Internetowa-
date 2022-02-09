function test(){
    let rowsnumer = document.getElementsByClassName("tablebody").length;
    let pages;

    if(rowsnumer%5!=0){
        pages=parseInt(rowsnumer/5)+1;
    }else{
        pages=parseInt(rowsnumer/5);
    }
    let text="";
    for(let x=0;x<pages;x++){
        text+="<input type='button' value=";
        text+=x;
        


    }

    //alert(pages);
    document.getElementById("links").innerHTML=text;


 
}