<template>
<div>
  <img src="./assets/logo.png" alt="logo">

  <Student></Student>
  <School></School>
</div>
</template>

<script>
import School from "@/components/School";
import Student from "@/components/Student";
export default {
  name:'App',
  components: {School, Student},
  component:{
    School,
    Student
  }
}
</script>