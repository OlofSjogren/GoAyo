const router = require('express').Router();
const fs = require('fs')
const joi = require('joi')

const schema = joi.object().keys({
    name: joi.string().required(),
    phonenumber: joi.string().max(10).required(),
    password: joi.string().required(),
    contacts: joi.array().length(0).required()
})

const contactSchema = joi.object().keys({
    userPhoneNumber: joi.string().required(),
    contactPhoneNumber: joi.string().required()
})

router.get('/', (req,res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const number = req.query.number
    const user = database.users.find(user => {
        return user.phonenumber === number
    })

    if(typeof number === 'undefined' || typeof user === 'undefined'){
        res.status(400)
        res.send("BAD REQUEST, INCORRECT NUMBER")
    }
    res.send(user);
})

router.post('/', (req,res) =>{
    const database = JSON.parse(fs.readFileSync('database.json'))
    let newUser = req.body;
    database.users.push(newUser);
    const validation = schema.validate(newUser);

    if(!validation.error){
        fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
        res.status(200)
        res.send(newUser)
    } else {
        res.status(422)
        res.send(validation.error)
    }

})

router.post('/addContact', (req, res) => {

    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = contactSchema.validate(req.body)

    if(!validation.error){
        const number = req.body.userPhoneNumber;
        const userIndex = database.users.findIndex(user => {
            return user.phonenumber === number
        })
        if(userIndex === -1){
            res.status(400)
            res.send("BAD REQUEST, INCORRECT NUMBER")
        } else {
            database.users[userIndex].contacts.push(req.body.contactPhoneNumber)
            fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
            res.send("added " + req.body.contactPhoneNumber + " to " + database.users[userIndex].name)
        }
    } else {
        req.status(422)
        req.send(validation.error)
    }
})

router.post('/removeContact', (req, res) => {

    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = contactSchema.validate(req.body)

    if(!validation.error){
        const number = req.body.userPhoneNumber;
        const contactNumber = req.body.contactPhoneNumber
        const userIndex = database.users.findIndex(user => {
            return user.phonenumber === number
        })
        if(userIndex === -1){
            res.status(400)
            res.send("BAD REQUEST, INCORRECT NUMBER")
        } else {
            const contactIndex = database.users[userIndex].contacts.findIndex(contact => {
                return contact === contactNumber
            })
            if(contactIndex === -1){
                res.status(400)
                res.send("BAD REQUEST, INCORRECT CONTACT")
            } else {
                database.users[userIndex].contacts.splice(contactIndex, 1);
                fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
                res.send("removed " + contactNumber + " from " + database.users[userIndex].name)
            }
        }
    } else {
        req.status(422)
        req.send(validation.error)
    }
})

module.exports = router;
