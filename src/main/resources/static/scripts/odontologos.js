window.addEventListener('load', (e) => {
    
    let botonListado = document.querySelector("#listado-odon"),
        botonNuevoOdon = document.querySelector("#nuevo-odon"),
        formBuscar = document.querySelector("#form-buscar"),
        inputID = document.querySelector("#id-buscar"),
        resultado = document.querySelector("#resultado"),
        listadoOdon = document.querySelector(".listado"),
        sectionRegistro = document.querySelector(".datos");

    //buscar todos los odontologos
    botonListado.addEventListener('click', (e) => {
        
        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        fetch(`http://localhost:8080/odontologos`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            console.log(info)
            sectionRegistro.innerHTML = `<div class="odon">
                                         <h3>Odontólogos</h3>
                                         <div class="lista"  id="lista-odon">
                                         </div>
                                         </div>`
            let ulOdon = document.querySelector("#lista-odon");
            info.map((odon) => {
                ulOdon.innerHTML += `
                                     <ul class="odontologo">
                                     <li>ID ${odon.id}</li>
                                     <li>${odon.nombre} ${odon.apellido}</li>
                                     <li>Matrícula ${odon.matricula}</li>
                                     </ul>`
            })
        })
        .catch((error) => console.log(error))
    })


    //registrar nuevo odontologo
    botonNuevoOdon.addEventListener('click', (e) => {
        listadoOdon.innerHTML = "";
        sectionRegistro.innerHTML = `
                                     <div class="datosOdon">
                                        <form id="datosOdontologo">
                                        <label>Nombre</label>
                                        <input type="text" id="nombre" />
                                        <label>Apellido</label>
                                        <input type="text" id="apellido" />
                                        <label>Matrícula</label>
                                        <input type="text" id="matricula" />
                                        <button type="submit">Guardar</button>
                                     </form>
                                     </div>
                                     `
        
        let nombreOdon = document.querySelector("#nombre"),
            apellidoOdon = document.querySelector("#apellido"),
            matricula = document.querySelector("#matricula"),
            formulario = document.querySelector("#datosOdontologo");
        
        formulario.addEventListener('submit', (e) => {
            e.preventDefault();

            let datosOdontologo = {
                nombre : nombreOdon.value,
                apellido : apellidoOdon.value,
                matricula : matricula.value 
            }
    
            let config = {
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body : JSON.stringify(datosOdontologo)
            }

            fetch(`http://localhost:8080/odontologos/registro`, config) 
            .then((respuesta) => respuesta.json())
            .then((info) => {
                resultado.innerHTML = `<div class="exito">
                                            <h4>Guardado con éxito!</h4>
                                            <p>Odontólogo: ${info.nombre} ${info.apellido}</p>
                                            <p>Matrícula: ${info.matricula}</p>
                                        </div>`
                formulario.reset();
            })
            .catch((error) => console.log(error))
        })
    })


    //buscar odontologo por id --> modificar/eliminar
    formBuscar.addEventListener('submit', (e) => {
        e.preventDefault();

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        fetch(`http://localhost:8080/odontologos/${inputID.value}`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            listadoOdon.innerHTML = `
                                     <div class="odon">
                                     <h3>Odontólogos</h3>
                                     <ul class="odontologo" id="lista-odon">
                                        <li>${info.nombre} ${info.apellido}</li>
                                        <li>Matrícula ${info.matricula}</li>
                                        <button type="submit" id="idModificar">Modificar</button>
                                        <button type="submit" id="idEliminar">Eliminar</button>
                                     </ul>
                                     </div>

                                     `
            formBuscar.reset();

            //modificar el odontologo buscado
            let botonModificar = document.querySelector("#idModificar");

            botonModificar.addEventListener('click', (e) => {
                let sectionRegistro = document.querySelector(".datos");

                sectionRegistro.innerHTML = `<form id="modifOdontologo">
                                                <label>Nombre</label>
                                                <input type="text" id="nombre" />
                                                <label>Apellido</label>
                                                <input type="text" id="apellido" />
                                                <label>Matrícula</label>
                                                <input type="text" id="matricula" />
                                                <button type="submit">Guardar</button>
                                             </form>`
                                             
                document.querySelector("#nombre").value = info.nombre;
                document.querySelector("#apellido").value = info.apellido;
                document.querySelector("#matricula").value = info.matricula;

                let formModificar = document.querySelector("#modifOdontologo");
                formModificar.addEventListener('submit', (e) => {
                    e.preventDefault();

                    let datosOdontologo = {
                        id : info.id,
                        nombre : document.querySelector("#nombre").value,
                        apellido : document.querySelector("#apellido").value,
                        matricula : document.querySelector("#matricula").value 
                    }
            
                    let config = {
                        method : "PUT",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body : JSON.stringify(datosOdontologo)
                    }

                    fetch(`http://localhost:8080/odontologos/modificar`, config) 
                    .then((respuesta) => respuesta.json())
                    .then((info) => {
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Guardado con éxito!</h4>
                                                    <p>Odontólogo: ${info.nombre} ${info.apellido}</p>
                                                    <p>Matrícula: ${info.matricula}</p>
                                                </div>`
                        formModificar.reset();
                    })
                    .catch((error) => console.log(error))

                })
            })


            //borrar el odontologo buscado
            let botonBorrar = document.querySelector("#idEliminar");
            botonBorrar.addEventListener('click', (e) => {

                let config = {
                    method : "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body : ""
                }

                fetch(`http://localhost:8080/odontologos/borrar/${info.id}`, config)
                .then((respuesta) => {
                    if(respuesta.status === 204) {
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Eliminado con éxito!</h4>
                                                <div>`
                        listadoOdon.innerHTML = "";
                    }
                })
                .then((info) => console.log(info))
                .catch((error) => console.log(error))

            })


        })
        .catch((error) => console.log(error))

    })












})