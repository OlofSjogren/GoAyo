const router = require('express').Router();
const fs = require('fs')
const uuid = require('uuid')
const joi = require('joi')

const schema = joi.object().keys({
    groupId: joi.string().required(),
    debt: joi.object({
        lender: joi.object().keys({
            name: joi.string().required(),
            phonenumber: joi.string().max(10).required(),
            password: joi.string().required(),
            contacts: joi.array().required()
        }).required(),
        borrower: joi.object().keys({
            name: joi.string().required(),
            phonenumber: joi.string().max(10).required(),
            password: joi.string().required(),
            contacts: joi.array().required()
        }).required(),
        date: joi.string().required(),
        owed: joi.string().required(),
        id: joi.string().required(),
        payments: joi.array().length(0).required(),
        description: joi.string().required()
    })
})

router.get('/', (req,res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const groupID = req.query.groupId;
    const debtID = req.query.debtId;

    const group = database.groups.find(group => {
        return group.id === groupID;
    })
    if (typeof group === 'undefined'){
        res.status(422)
        res.send("GROUP NOT FOUND BY ID")
    }

    const debt = group.debts.find(debt => {
        return debt.id === debtID
    })
    if (typeof debt === 'undefined'){
        res.status(422)
        res.send("DEBT NOT FOUND BY ID")
    }

    res.send(debt)
})

router.post('/', (req,res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = schema.validate(req.body);

    if(!validation.error){
        const newDebt = req.body.debt;
        const groupId = req.body.groupId;
        const groupIndex = database.groups.findIndex(group => group.id === groupId)

        if (groupIndex === -1){
            res.status(422)
            res.send("GROUP NOT FOUND BY ID")
        } else if(typeof groupIndex === "undefined"){
            res.status(422)
            res.send("")
            console.log(req.body)
        } else {
            database.groups[groupIndex].debts.push(newDebt);
            fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
            console.log(newDebt.id + " Was added!")
            res.send(newDebt)
        }

    } else {
        res.status(422)
        res.send(validation.error)
    }
})

module.exports = router;