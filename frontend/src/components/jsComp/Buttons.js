import { h } from 'vue';
import { NButton } from 'naive-ui';
import {Edit16Filled, Delete20Filled, CalendarMonth20Filled} from '@vicons/fluent';
class Buttons {

    editButton(entity, openEditDialog) {
        return h(
            NButton,
            {
                quaternary: true,
                onClick: () => openEditDialog(entity),
            },
            {
                icon: () => h(Edit16Filled)
            }
        );
    }

    deleteButton(entity, openDeleteDialog) {
        return h(
            NButton,
            {
                quaternary: true,
                onClick: () => openDeleteDialog(entity),
            },
            {
                icon: () => h(Delete20Filled)
            }
        );
    }

    calendarButton(entity, openCalendar) {
        return h(
            NButton,
            {
                quaternary: true,
                onClick: () => openCalendar(entity),
            },
            {
                icon: () => h(CalendarMonth20Filled)
            }
        );
    }
}

export default new Buttons();