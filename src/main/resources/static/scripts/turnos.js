window.addEventListener('load', (e) => {

    let botonListado = document.querySelector("#listado-turnos"),
        botonSemanales = document.querySelector("#ts"),
        botonNuevoTurno = document.querySelector("#nuevo-turno"),
        formBuscar = document.querySelector("#form_buscar"),
        inputID = document.querySelector("#id_buscar"),
        resultado = document.querySelector("#resultado"),
        listadoTurnos = document.querySelector(".listado"),
        sectionRegistro = document.querySelector(".datos");

    //buscar todos los turnos
    botonListado.addEventListener('click', (e) => {

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        fetch(`http://localhost:8080/turnos`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
           
            sectionRegistro.innerHTML =  `
                                        <div class="turn">
                                        <h3>Turnos</h3>
                                        <div class="lista" id="lista-turnos"></div>
                                        </div>`;
            let ulTurnos = document.querySelector("#lista-turnos");
            info.map((turno) => {
                ulTurnos.innerHTML += `<ul class = "turno">
                                       <li>ID ${turno.id}</li>
                                       <li>Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}</li>
                                       <li>Fecha: ${turno.fechaHora.slice(0, 10).split('-').reverse().join('-')}</li>
                                       <li>Horario: ${turno.fechaHora.slice(11, 16)}hs</li>
                                       <li>Odontólogo: ${turno.odontologo.nombre} ${turno.odontologo.apellido}</li>
                                       </ul>`
            })
        })
        .catch((error) => console.log(error))
    })


    //buscar turnos semanales
    botonSemanales.addEventListener('click', (e) => {
        
        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";

        fetch(`http://localhost:8080/turnos/semanales`)
        .then((respuesta) => respuesta.json())
        .then((info) => {

            sectionRegistro.innerHTML =  `<div class="turn">
                                          <h3>Turnos Semanales</h3>
                                          <div class="lista" id="lista-turnos"></div>
                                          </div>`;
            let ulTurnos = document.querySelector("#lista-turnos");
            info.map((turno) => {
                ulTurnos.innerHTML += `<ul class = "turno">
                                         <li>ID ${turno.id}</li>
                                         <li>Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}</li>
                                         <li>Fecha: ${turno.fechaHora.slice(0, 10).split('-').reverse().join('-')}</li>
                                         <li>Horario: ${turno.fechaHora.slice(11, 16)}hs</li>
                                         <li>Odontólogo: ${turno.odontologo.nombre} ${turno.odontologo.apellido}</li>
                                       </ul>`
            })
        })
        .catch((error) => console.log(error))
    })
    
        

    //registrar nuevo turno
    botonNuevoTurno.addEventListener('click', (e) => {
        listadoTurnos.innerHTML = "";
        sectionRegistro.innerHTML = `
                                    <div class ="datosTur">
                                    <form id="datosTurno">
                                        <label>Fecha</label>
                                        <input type="text" id="fecha" placeholder="Ej AAAA-MM-DD"/>
                                        <label>Hora</label>
                                        <input type="text" id="hora" placeholder="Ej HH:MM"/>
                                        <label>ID Paciente</label>
                                        <input type="text" id="id-pac" />
                                        <label>ID Odontólogo</label>
                                        <input type="text" id="id-odon" />
                                        <button type="submit">Guardar</button>
                                    </form>
                                    </div>
                                    `
        let fechaTurno = document.querySelector("#fecha"),
            horaTurno = document.querySelector("#hora"),
            idPaciente = document.querySelector("#id-pac"),
            idOdontologo = document.querySelector("#id-odon"),
            formulario = document.querySelector("#datosTurno");

        formulario.addEventListener('submit', (e) => {
            e.preventDefault();
                
            let datosTurno = {
                fechaHora : fechaTurno.value + " " + horaTurno.value,
                odontologo : {
                    id: idOdontologo.value
                },
                paciente : {
                    id: idPaciente.value
                }
            }
        
            let config = {
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body : JSON.stringify(datosTurno)
            }

            fetch(`http://localhost:8080/turnos/registro`, config) 
            .then((respuesta) => respuesta.json())
            .then((info) => {
               
                resultado.innerHTML = `<div class="exito">
                                            <h4>Turno guardado con éxito!</h4>
                                            <p>Fecha: ${info.fechaHora.slice(0, 10).split('-').reverse().join('-')} - Horario: ${info.fechaHora.slice(11, 16)}hs</p>
                                        </div>`
                formulario.reset();
            })
            .catch((error) => console.log(error))
        })

    })


    //buscar turno por id --> modificar/eliminar
    formBuscar.addEventListener('submit', (e) => {
        e.preventDefault();

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        fetch(`http://localhost:8080/turnos/${inputID.value}`)
        .then((response) => response.json())
        .then((info) => {

            listadoTurnos.innerHTML = `
                                        <div class="turn">
                                           <h3>Turnos</h3>
                                           <ul class="turno" id="lista-turnos">
                                               <li>Paciente: ${info.paciente.nombre} ${info.paciente.apellido} - Fecha: ${info.fechaHora.slice(0, 10).split('-').reverse().join('-')} - Horario: ${info.fechaHora.slice(11, 16)}hs - Odontólogo: ${info.odontologo.nombre} ${info.odontologo.apellido}</li>
                                               <button type="submit" id="idModificar">Modificar</button>
                                               <button type="submit" id="idEliminar">Eliminar</button>
                                           </ul>
                                        </div>`
            formBuscar.reset();

            //modificar el turno buscado
            let botonModificar = document.querySelector("#idModificar");
            botonModificar.addEventListener('click', (e) => {
                sectionRegistro.innerHTML = `<form id="modifTurno">
                                                <label>Fecha</label>
                                                <input type="text" id="fecha"/>
                                                <label>Hora</label>
                                                <input type="text" id="hora"/>
                                                <label>ID Paciente</label>
                                                <input type="text" id="id-pac" />
                                                <label>ID Odontólogo</label>
                                                <input type="text" id="id-odon" />
                                                <button type="submit">Guardar</button>
                                            </form>`
                document.querySelector("#fecha").value = info.fechaHora.slice(0, 10).split('-').reverse().join('-');
                document.querySelector("#hora").value = info.fechaHora.slice(11, 16);
                document.querySelector("#id-pac").value = info.paciente.id;
                document.querySelector("#id-odon").value = info.odontologo.id;
                
                let formModificar = document.querySelector("#modifTurno");
                formModificar.addEventListener('submit', (e) => {
                    e.preventDefault();

                    let fechaHora = document.querySelector("#fecha").value.split('-').reverse().join('-') + " " + document.querySelector("#hora").value;
                    
                    let datosTurno = {
                        id : info.id,
                        fechaHora : fechaHora,
                        odontologo : {
                            id: document.querySelector("#id-odon").value
                        },
                        paciente : {
                            id: document.querySelector("#id-pac").value
                        } 
                    }
            
                    let config = {
                        method : "PUT",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body : JSON.stringify(datosTurno)
                    }
                    
                    fetch(`http://localhost:8080/turnos/modificar`, config) 
                    .then((respuesta) => respuesta.json())
                    .then((info) => {
                        sectionRegistro.innerHTML = `<div class="exito">
                                                     <h4>Guardado con éxito!</h4>
                                                     <p>Fecha: ${info.fechaHora.slice(0, 10).split('-').reverse().join('-')} - Horario: ${info.fechaHora.slice(11, 16)}hs</p>
                                                     </div>`
                        formModificar.reset();
                    })
                    .catch((error) => console.log(error))
                    
                })
                
            })

            //borrar el turno buscado
            let botonBorrar = document.querySelector("#idEliminar");
            botonBorrar.addEventListener('click', (e) => {
                let config = {
                    method : "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body : ""
                }

                fetch(`http://localhost:8080/turnos/borrar/${info.id}`, config)
                .then((respuesta) => {
                    if(respuesta.status === 204) {
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Eliminado con éxito!</h4>
                                                <div>`;
                        listadoTurnos.innerHTML = "";
                    }
                })
                .then((info) => console.log(info))
                .catch((error) => console.log(error))
            })

        })
        .catch((error) => console.log(error))
        
    })

})