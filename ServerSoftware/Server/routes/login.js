const router = require('express').Router();
const fs = require('fs')

router.get('/', (req,res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const number = req.query.number

    const user = database.users.find(user => {
        return user.phonenumber === number
    })

    if(typeof user === 'undefined'){
        res.status(404)
        res.send("USER NOT FOUND BY NUMBER")
    }

    if(user.password === req.query.password){
        res.send(user)
    } else {
        res.status(404)
        res.send("ERRPASSWORD")
    }
})

module.exports = router