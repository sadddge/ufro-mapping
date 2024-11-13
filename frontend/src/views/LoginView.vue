<template>
    <div class="container mx-auto pt-64 w-1/4 flex flex-col gap-10">
        <h1 class="text-xl">Login</h1>
        <n-form ref="formRef" :model="model" :rules="rules">
            <n-form-item path="username" label="Username">
                <n-input v-model:value="model.username" />
            </n-form-item>
            <n-form-item path="password" label="Password">
                <n-input type="password" />
            </n-form-item>
            <n-button type="primary" secondary>Login</n-button>
        </n-form>
    </div>
</template>

<script setup>
import { ref } from 'vue'
const formRef = ref(null)
const model = ref({
    username: null,
    password: null
})

const rules = {
    username: [
        {
            required: true,
            message: 'Username is required',
            trigger: ['input']
        }
    ],
    password: [
        {
            required: true,
            message: 'Password is required',
            trigger: ['input']
        }
    ]
}
</script>

<style scoped></style>