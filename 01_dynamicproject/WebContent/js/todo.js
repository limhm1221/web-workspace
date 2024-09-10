const likeIcons = document.querySelector(".like>span");
likeIcons.addEventListener("click",function(){
    const current = likeIcons.innerText;
    if(current === "favorite"){
        likeIcons.innerText = "favorite_border"
    }else{
        likeIcons.innerText = "favorite";
    }
});

const doneIcon = document.querySelector(".done");
doneIcon.addEventListener("click",function(){
    const todoText = document.querySelector(".todo-text");
    todoText.style.textDecoration = "line-through";
});
const cancelIcon = document.querySelector(".cancel");
cancelIcon.addEventListener("click",function(){
    const todo = document.querySelector(".todo");
    todo.remove();
});

const addBtn = document.querySelector("#add-btn");
const input = document.querySelector("#input");

input.addEventListener("keyup",function(event){
    if(event.keyCode == 13){
        todoAdd();
        input.value = "";
    }
    
});

addBtn.addEventListener("click",function(){
    todoAdd();
    const input = document.querySelector("#input");
    input.value = "";
});

function todoAdd(){
    const input = document.querySelector("#input");
    const inputValue = input.value;

    if(inputValue === ""){
        return;
    }

    const ul = document.createElement("ul");

    const likeLi = document.createElement("li");
    const textLi = document.createElement("li");
    const checkLi = document.createElement("li");

    const likeSpan = document.createElement("span");//<span></span>
    const doneSpan = document.createElement("span");
    const cancelSpan = document.createElement("span");
    
    likeSpan.classList.add("material-icons");
    likeSpan.innerText = "favorite_border";

    doneSpan.classList.add("material-icons");
    doneSpan.classList.add("done");
    doneSpan.innerText = "check_circle";

    doneSpan.addEventListener("click",function(){
        textLi.style.textDecoration = "line-through";
    })

    cancelSpan.classList.add("material-icons");
    cancelSpan.classList.add("cancel");
    cancelSpan.innerText = "cancel";

    cancelSpan.addEventListener("click",function(){
        ul.remove();
    })

    likeLi.classList.add("like")    
    likeLi.appendChild(likeSpan);

    likeSpan.addEventListener("click",function(){
        const current = likeSpan.innerText;
        if(current == "favorite"){
            likeSpan.innerText = "favorite_border";
        }else{
            likeSpan.innerText = "favorite";
        }
    })

    textLi.classList.add("todo-text");
    textLi.innerText = inputValue;

    checkLi.classList.add("check-zone");
    checkLi.appendChild(doneSpan);
    checkLi.appendChild(cancelSpan);

    ul.classList.add("todo");

    ul.appendChild(likeLi);
    ul.appendChild(textLi);
    ul.appendChild(checkLi);

    const listBox = document.querySelector(".list-box");
    listBox.appendChild(ul);
}