import {h} from "vue";
import {NForm, NFormItem, NInput, NInputNumber, NSelect, NSpace} from "naive-ui";

export const editUserContent = (user) => {
    const rules = {
        nombre: {
            required: true,
            message: 'El nombre es requerido',
            trigger: 'blur'
        },
        correo: {
            required: true,
            message: 'El correo es requerido',
            trigger: 'blur'
        }
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h('div', '  '),
        h(NForm, {model: user, rules: rules}, [
            h(NFormItem, {label: 'Nombre', path: 'nombre'}, [
                h(NInput, {
                    value: user.nombre,
                    onUpdateValue: (val) => (user.nombre = val),
                    placeholder: 'Nombre del usuario',
                }),
            ]),
            h(NFormItem, {label: 'Correo', path: 'correo'}, [
                h(NInput, {
                    value: user.correo,
                    onUpdateValue: (val) => (user.correo = val),
                    placeholder: 'Correo del usuario',
                }),
            ]),
        ]),
    ]);
}


export const newUserContent = (user) => {
    const rules = {
        nombre: {
            required: true,
            message: 'El nombre es requerido',
            trigger: 'blur'
        },
        correo: {
            required: true,
            message: 'El correo es requerido',
            trigger: 'blur'
        }
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h('div', '  '),
        h(NForm, {model: user, rules: rules}, [
            h(NFormItem, {label: 'Nombre', path: 'nombre'}, [
                h(NInput, {
                    value: user.nombre,
                    onUpdateValue: (val) => (user.nombre = val),
                    placeholder: 'Nombre del usuario',
                }),
            ]),
            h(NFormItem, {label: 'Correo', path: 'correo'}, [
                h(NInput, {
                    value: user.correo,
                    onUpdateValue: (val) => (user.correo = val),
                    placeholder: 'Correo del usuario',
                }),
            ]),
            h(NFormItem, {label: 'Contraseña', path: 'contrasena'}, [
                h(NInput, {
                    value: user.contrasenia,
                    onUpdateValue: (val) => (user.contrasenia = val),
                    placeholder: 'Contraseña',
                    type: 'password',
                }),
            ])
        ]),
    ]);
}


export const buildingContent = (building) => {
    const rules = {
        nombre: {
            required: true,
            message: 'El nombre es requerido',
            trigger: 'blur'
        },
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h('div', '  '),
        h(NForm, {model: building, rules: rules}, [
            h(NFormItem, {label: 'Nombre', path: 'nombre'}, [
                h(NInput, {
                    value: building.nombre,
                    onUpdateValue: (val) => (building.nombre = val),
                    placeholder: 'Nombre del edificio',
                }),
            ]),
            h(NFormItem, {label: 'Alias', path: 'alias'}, [
                h(NInput, {
                    value: building.alias,
                    onUpdateValue: (val) => (building.alias = val),
                    placeholder: 'Alias del edificio',
                }),
            ]),
            h(NFormItem, {label: 'Tipo', path: 'tipo'}, [
                h(NInput, {
                    value: building.tipo,
                    onUpdateValue: (val) => (building.tipo = val),
                    placeholder: 'Tipo del edificio',
                }),
            ]),
        ]),
    ])
}


export const classroomContent = (classroom, options) => {
    const rules = {
        nombre: {
            required: true,
            message: 'El nombre es requerido',
            trigger: 'blur'
        },
        edificioId: {
            required: true,
            message: 'El edificio es requerido',
            trigger: 'blur'
        }
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h(NForm, {model: classroom, rules: rules}, [
            h(NFormItem, {label: 'Nombre', path: 'nombre'}, [
                h(NInput, {
                    value: classroom.nombre,
                    onUpdateValue: (val) => (classroom.nombre = val),
                    placeholder: 'Nombre de la sala',
                })
            ]),
            h(NFormItem, {label: 'Edificio', path: 'edificioId'}, [
                h(NSelect, {
                    placeholder: 'Selecciona un edificio',
                    options: options,
                    value: classroom.edificioId,
                    onUpdateValue: (val) => (classroom.edificioId = val)
                }),
            ]),
        ])
    ]);
}


export const courseContent = (course) => {
    const rules = {
        nombre: {
            required: true,
            message: 'El nombre es requerido',
            trigger: 'blur'
        },
        codigo: {
            required: true,
            message: 'El código es requerido',
            trigger: 'blur'
        },
        descripcion: {
            required: false,
        },
        sct: {
            required: true,
            message: 'El SCT es requerido',
            trigger: 'blur'
        }
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h('div', '  '),
        h(NForm, {model: course, rules: rules}, [
            h(NFormItem, {label: 'Nombre', path: 'nombre'}, [
                h(NInput, {
                    value: course.nombre,
                    onUpdateValue: (val) => (course.nombre = val),
                    placeholder: 'Nombre de la asignatura',
                }),
            ]),
            h(NFormItem, {label: 'Código', path: 'codigo'}, [
                h(NInput, {
                    value: course.codigo,
                    onUpdateValue: (val) => (course.codigo = val),
                    placeholder: 'Código de la asignatura',
                }),
            ]),
            h(NFormItem, {label: 'Descripción', path: 'descripcion'}, [
                h(NInput, {
                    value: course.descripcion,
                    onUpdateValue: (val) => (course.descripcion = val),
                    placeholder: 'Descripción de la asignatura',
                }),
            ]),
            h(NFormItem, {label: 'SCT', path: 'sct'}, [
                h(NInputNumber, {
                    value: course.sct,
                    onUpdateValue: (val) => (course.sct = val),
                    validator: (val) => val > 0,
                    placeholder: 'SCT de la asignatura',
                }),
            ]),
        ]),
    ]);
}


export const lectureContent = (lecture, coursesOptions, buildingOptions, classroomsOptions) => {
    const rules = {
        asignatura: {
            nombre: {
                required: true,
                message: 'La asignatura es requerida',
                trigger: 'blur'
            }
        },
        sala: {
            edificio: {
                nombre: {
                    required: true,
                    message: 'El edificio es requerido',
                    trigger: 'blur'
                }
            },
            nombre: {
                required: true,
                message: 'La sala es requerida',
                trigger: 'blur'
            }
        },
        docente: {
            required: false
        },
        modulo: [{
            required: true,
            validator(rule, value) {
                if (!value) {
                    return new Error('El modulo es requerido')
                } else if (value <= 0) {
                    return new Error('El modulo debe ser mayor a 0')
                }
                return true
            },
            trigger: ["input", "blur"]
        }]
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h(NForm, {model: lecture, rules: rules}, [
            h(NFormItem, {label: 'Asignatura', path: 'asignatura.nombre'}, [
                h(NSelect, {
                    placeholder: 'Selecciona una asignatura',
                    options: coursesOptions,
                    value: lecture.asignatura.nombre,
                    onUpdateValue: (val) => {
                        lecture.asignatura.id = val.id
                        lecture.asignatura.nombre = val.nombre
                        lecture.asignatura.codigo = val.codigo
                    }
                }),
            ]),
            h(NFormItem, {label: 'Edificio', path: 'sala.edificio.nombre'}, [
                h(NSelect, {
                    placeholder: 'Selecciona un edificio',
                    options: buildingOptions,
                    value: lecture.sala.edificio.alias ? lecture.sala.edificio.alias : lecture.sala.edificio.nombre,
                    onUpdateValue: (val) => {
                        lecture.sala.edificio.id = val.id
                        lecture.sala.edificio.nombre = val.nombre
                        lecture.sala.edificio.alias = val.alias
                        lecture.sala.id = null
                        lecture.sala.nombre = null
                    },
                }),
            ]),
            h(NFormItem, {label: 'Sala', path: 'sala.nombre'}, [
                h(NSelect, {
                    placeholder: 'Selecciona una sala',
                    options: classroomsOptions(lecture.sala.edificio.id),
                    value: lecture.sala.nombre,
                    onUpdateValue: (val) => {
                        lecture.sala.nombre = val.nombre
                        lecture.sala.id = val.id
                    },
                    disabled: !lecture.sala.edificio.id
                }),
            ]),
            h(NFormItem, {label: 'Docente', path: 'docente'}, [
                h(NInput, {
                    value: lecture.docente,
                    onUpdateValue: (val) => (lecture.docente = val),
                    placeholder: 'Nombre del docente',
                }),
            ]),
            h(NFormItem, {label: 'Modulo', path: 'modulo'}, [
                h(NInputNumber, {
                    value: lecture.modulo,
                    onUpdateValue: (val) => (lecture.modulo = val),
                    validator: (val) => val > 0,
                    placeholder: 'Modulo',
                }),
            ]),
        ]),
    ]);
}

export const newInscripcionContent = (inscripcion, options) => {
    const rules = {
        asignaturaId: {
            required: true,
            message: 'La asignatura es requerida',
            trigger: 'blur'
        }
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h('div', '   '),
        h(NForm, {model: inscripcion, rules: rules}, [
            h(NFormItem, {label: 'Asignatura', path: 'asignaturaId'}, [
                h(NSelect, {
                    placeholder: 'Selecciona una asignatura',
                    options: options,
                    value: inscripcion.asignaturaId,
                    onUpdateValue: (val) => (inscripcion.asignaturaId = val),
                }),
            ])
        ]),
    ]);
}


export const newClassroomForBuildingContent = (classroom) => {
    const rules = {
        nombre: {
            required: true,
            message: 'El nombre es requerido',
            trigger: 'blur'
        }
    }
    return h(NSpace, {vertical: true, style: {width: '100%'}}, [
        h('div', '      '),
        h(NForm, {model: classroom, rules: rules}, [
            h(NFormItem, {label: 'Nombre', path: 'nombre'}, [
                h(NInput, {
                    value: classroom.nombre,
                    onUpdateValue: (val) => (classroom.nombre = val),
                    placeholder: 'Nombre de la sala',
                }),
            ]),
        ]),
    ]);
}
