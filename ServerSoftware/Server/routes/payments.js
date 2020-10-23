const router = require('express').Router();
const fs = require('fs');
const joi = require('joi');
const uuid = require('uuid');

const schema = joi.object().keys({
    debtId: joi.string().required(),
    groupId: joi.string().required(),
    payment: joi.object({
        date: joi.string().required(),
        amount: joi.string().required(),
        id: joi.string().required()
    })
    })


router.get('/', (req,res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))

    const debtId = req.query.debtId;
    const groupId = req.query.groupId;
    const paymentId = req.query.paymentId;

    const groupIndex = database.groups.findIndex(group => group.id === groupId);
    if(groupIndex === -1){
        res.status(422)
        res.send("GROUP NOT FOUND BY ID")
    }

    const debtIndex = database.groups[groupIndex].debts.findIndex(debt => debt.id === debtId);
    if(debtIndex === -1){
        res.status(422)
        res.send("DEBT NOT FOUND BY ID")
    } else if (typeof paymentId !== 'undefined'){
        const paymentIndex = database.groups[groupIndex].debts[debtIndex].payments.findIndex(payment => payment.id === paymentId)
        if(paymentIndex === -1){
            res.status(422)
            res.send("PAYMENT NOT FOUND BY ID")
        } else {
            const payment = database.groups[groupIndex].debts[debtIndex].payments[paymentIndex];
            res.send(payment);
        }
    } else {
        const payments = database.groups[groupIndex].debts[debtIndex].payments;
        res.send(payments);
    }
})

router.post('/', (req,res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = schema.validate(req.body);

    if(!validation.error){

        const debtId = req.body.debtId;
        const groupId = req.body.groupId;
        const newPayment = req.body.payment
        let debtIndex;

        const groupIndex = database.groups.findIndex(group => group.id === groupId);
        if(groupIndex === -1){
            res.status(422)
            res.send("GROUP NOT FOUND BY ID")
        } else {
            debtIndex = database.groups[groupIndex].debts.findIndex(debt => debt.id === debtId);
            if (debtIndex === -1) {
                res.status(422)
                res.send("DEBT NOT FOUND BY ID")
            }
        }

        database.groups[groupIndex].debts[debtIndex].payments.push(newPayment);
        fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
        res.send(newPayment);
    } else {
        res.status(422);
        res.send(validation.error);
    }
})

module.exports = router