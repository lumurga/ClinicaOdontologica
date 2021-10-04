window.addEventListener('load', (e) => {
    let botonListado = document.querySelector("#listado-pac"),
        botonNuevoPac = document.querySelector("#nuevo-pac"),
        formBuscar = document.querySelector("#formBuscar"),
        inputID = document.querySelector("#idBuscar"),
        resultado = document.querySelector("#resultado"),
        listadoPac = document.querySelector(".listado"),
        sectionRegistro = document.querySelector(".datos");

    //buscar todos los pacientes
    botonListado.addEventListener('click', (e) => {
        
        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        fetch(`http://localhost:8080/pacientes`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            
            sectionRegistro.innerHTML = `
                                         <div class="pac">
                                         <h3>Pacientes</h3>
                                         <div class="lista" id="lista-pac">
                                         </div>
                                         </div>`


            let ulPac = document.querySelector("#lista-pac");
            info.map((pac) => {
                ulPac.innerHTML += `
                                    <ul class="paciente">
                                         <li>ID ${pac.id}</li>
                                         <li>${pac.nombre} ${pac.apellido}</li>
                                         <li>DNI ${pac.dni}</li>
                                         <li>Fecha de ingreso: ${pac.fechaIngreso.split('-').reverse().join('/')}</li>
                                         <li>Domicilio: ${pac.domicilio.calle} ${pac.domicilio.numero},  ${pac.domicilio.localidad},  ${pac.domicilio.provincia}</li>
                                    </ul>`

                })
        })
        .catch((error) => console.log(error))
    })


    //registrar nuevo paciente
    botonNuevoPac.addEventListener('click', (e) => {
        listadoPac.innerHTML = "";
        sectionRegistro.innerHTML = `<div class="datosPac">
                                        <form id="datosPaciente">
                                        <label>Nombre</label>
                                        <input type="text" id="nombre" />
                                        <label>Apellido</label>
                                        <input type="text" id="apellido" />
                                        <label>DNI</label>
                                        <input type="text" id="dni" />
                                        <br>
                                        <br>
                                        <label>Domicilio:</label>
                                        <br>
                                        <label>Calle</label>
                                        <input type="text" id="calle" />
                                        <label>Número</label>
                                        <input type="text" id="numero" />
                                        <label>Localidad</label>
                                        <input type="text" id="localidad" />
                                        <label>Provincia</label>
                                        <input type="text" id="provincia" />
                                        <button type="submit">Guardar</button>
                                    </form>
                                    </div>
                                    `
            
        let nombrePac = document.querySelector("#nombre"),
            apellidoPac = document.querySelector("#apellido"),
            dni = document.querySelector("#dni"),
            calle = document.querySelector("#calle"),
            numero = document.querySelector("#numero"),
            localidad = document.querySelector("#localidad"),
            provincia = document.querySelector("#provincia"),
            formulario = document.querySelector("#datosPaciente");
            
        formulario.addEventListener('submit', (e) => {
            e.preventDefault();
    
            let datosPaciente = {
                nombre : nombrePac.value,
                apellido : apellidoPac.value,
                dni : dni.value,
                domicilio : {
                    calle : calle.value,
                    numero : numero.value,
                    localidad : localidad.value,
                    provincia : provincia.value
                } 
            }
        
            let config = {
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body : JSON.stringify(datosPaciente)
            }
    
            fetch(`http://localhost:8080/pacientes/registro`, config) 
            .then((respuesta) => respuesta.json())
            .then((info) => {
                resultado.innerHTML = `<div class="exito">
                                            <h4>Guardado con éxito!</h4>
                                            <p>Paciente: ${info.nombre} ${info.apellido}</p>
                                            <p>DNI: ${info.dni}</p>
                                        </div>`
                formulario.reset();
            })
            .catch((error) => console.log(error))
        })
    })


    //buscar paciente por id --> modificar/eliminar
    formBuscar.addEventListener('submit', (e) => {
        e.preventDefault();

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        fetch(`http://localhost:8080/pacientes/${inputID.value}`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            listadoPac.innerHTML = `
                                     <div class="pac">
                                     <h3>Pacientes</h3>
                                     <ul class="paciente" id="lista-pac">
                                        <li>${info.nombre} ${info.apellido}, DNI ${info.dni}</li>
                                        <button type="submit" id="idModificar">Modificar</button>
                                        <button type="submit" id="idEliminar">Eliminar</button>
                                     </ul>
                                     </div>

                                     `
            formBuscar.reset();

            //modificar el paciente buscado
            let botonModificar = document.querySelector("#idModificar");

            botonModificar.addEventListener('click', (e) => {

                sectionRegistro.innerHTML = `<form id="modifPaciente">
                                                <label>Nombre</label>
                                                <input type="text" id="nombre" />
                                                <label>Apellido</label>
                                                <input type="text" id="apellido" />
                                                <label>DNI</label>
                                                <input type="text" id="dni" />
                                                <br>
                                                <label>Domicilio:</label>
                                                <br>
                                                <br>
                                                <label>Calle</label>
                                                <input type="text" id="calle" />
                                                <label>Número</label>
                                                <input type="text" id="numero" />
                                                <label>Localidad</label>
                                                <input type="text" id="localidad" />
                                                <label>Provincia</label>
                                                <input type="text" id="provincia" />                                              
                                                <button type="submit">Guardar</button>
                                             </form>`
                                             
                document.querySelector("#nombre").value = info.nombre;
                document.querySelector("#apellido").value = info.apellido;
                document.querySelector("#dni").value = info.dni;
                document.querySelector("#calle").value = info.domicilio.calle;
                document.querySelector("#numero").value = info.domicilio.numero;
                document.querySelector("#localidad").value = info.domicilio.localidad;
                document.querySelector("#provincia").value = info.domicilio.provincia;

                let formModificar = document.querySelector("#modifPaciente");
                formModificar.addEventListener('submit', (e) => {
                    e.preventDefault();

                    let datosPaciente = {
                        id : info.id,
                        nombre : document.querySelector("#nombre").value,
                        apellido : document.querySelector("#apellido").value,
                        dni : document.querySelector("#dni").value,
                        fechaIngreso : info.fechaIngreso,
                        domicilio : {
                            calle : document.querySelector("#calle").value,
                            numero : document.querySelector("#numero").value,
                            localidad : document.querySelector("#localidad").value,
                            provincia : document.querySelector("#provincia").value
                        }  
                    }
            
                    let config = {
                        method : "PUT",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body : JSON.stringify(datosPaciente)
                    }

                    fetch(`http://localhost:8080/pacientes/modificar`, config) 
                    .then((respuesta) => respuesta.json())
                    .then((info) => {
                        sectionRegistro.innerHTML = `<div class="exito">
                                                     <h4>Guardado con éxito!</h4>
                                                     <p>Paciente: ${info.nombre} ${info.apellido}</p>
                                                     <p>DNI: ${info.dni}</p>
                                                     </div>`
                        formModificar.reset();
                    })
                    .catch((error) => console.log(error))

                })
            })


            //borrar el paciente buscado
            let botonBorrar = document.querySelector("#idEliminar");
            botonBorrar.addEventListener('click', (e) => {

                let config = {
                    method : "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body : ""
                }

                fetch(`http://localhost:8080/pacientes/borrar/${info.id}`, config)
                .then((respuesta) => {
                    if(respuesta.status === 204) {
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Eliminado con éxito!</h4>
                                                <div>`;
                        listadoPac.innerHTML = "";
                    }
                })
                .then((info) => console.log(info))
                .catch((error) => console.log(error))

            })


        })
        .catch((error) => console.log(error))

    })

})