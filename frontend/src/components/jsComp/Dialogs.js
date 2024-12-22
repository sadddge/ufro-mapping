export const openEditDialog = (dialog, entity, name, content, save) => {
    const clon = reactive(Object.assign({}, entity));
    dialog.create({
        title: 'Editar ' + name,
        content: () => content(clon),
        positiveText: 'Guardar',
        negativeText: 'Cancelar',
        showIcon: false,
        closable: false,
        onPositiveClick: () => save(clon),
    });
};

export const openDeleteDialog = (dialog, entity, name, confirm) => {
    dialog.error({
        title: 'Eliminar ' + name,
        content: '¿Estás seguro de que deseas eliminar esta entidad?',
        positiveText: 'Eliminar',
        negativeText: 'Cancelar',
        closable: false,
        onPositiveClick: () => confirm(entity),
    });
};

export const openNewEntityDialog = (dialog, entity, title, content, confirm) => {
    dialog.create({
        title: title,
        content: () => content(entity),
        positiveText: 'Guardar',
        negativeText: 'Cancelar',
        showIcon: false,
        closable: false,
        onPositiveClick: () => confirm(entity),
    });
};

export const openErorrDialog = (dialog, title, content, confirm) => {
    dialog.error({
        title: title,
        content: content,
        positiveText: 'Aceptar',
        negativeText: 'Cancelar',
        onPositiveClick: () => confirm(),
        showIcon: false,
        closable: false,
    });
}
export const openConfirmDialog = (dialog, title, content, confirm) => {
}