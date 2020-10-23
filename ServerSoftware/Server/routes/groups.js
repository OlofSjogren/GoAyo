const router = require('express').Router();
const fs = require('fs')
const joi = require('joi')
const uuid = require('uuid')

const schema = joi.object().keys({
    name: joi.string().required(),
    date: joi.string().max(10).min(10).required(),
    id: joi.string().required(),
    members: joi.array().items(joi.object().keys({
        name: joi.string().required(),
        phonenumber: joi.string().max(10).required(),
        password: joi.string().required(),
        contacts: joi.array().required()
    })).required(),
    debts: joi.array().length(0).required()
})

const addMemberSchema = joi.object().keys({
    member: joi.object().keys({
        name: joi.string().required(),
        phonenumber: joi.string().max(10).required(),
        password: joi.string().required(),
        contacts: joi.array().required()
    }).required(),
    groupId: joi.string().required()
})

router.get('/', (req, res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const user = req.query.number;
    const id = req.query.groupId;
    const groupWithId = database.groups.find(group => {
        return group.id === id;
    })

    if(typeof groupWithId !== 'undefined'){
        res.send(groupWithId)
    } else {
        const groupsWithNumber = database.groups.filter(group => {
            const numberChecker = (element) => element.phonenumber === user;
            return group.members.some(numberChecker)
        })

        console.log("1")
        if (groupsWithNumber.length === 0 && typeof req.query.number !== 'undefined') {
            res.status(206)
            res.send({
                groupJsonObjects: []
            })
            console.log("2")
        } else if (typeof groupWithId === "undefined" && typeof req.query.id !== 'undefined') {
            res.status(422)
            res.send("QUERIED ID NOT FOUND IN ANY GROUPS")
            console.log("3")
        } else if (groupsWithNumber.length !== 0) {
            console.log("4")
            console.log(groupsWithNumber)
            res.send({
                groupJsonObjects: groupsWithNumber
            })
        } else {
            res.status(400)
            res.send("BAD QUERY");
        }
    }
})

router.post('/', (req, res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = schema.validate(req.body)

    if (!validation.error) {
        let newGroup = req.body;
        database.groups.push(newGroup);
        fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
        res.status(200)
        res.send(newGroup)
    } else {
        res.status(422)
        res.send(validation.error)
    }
})

router.post('/addMember', (req, res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = addMemberSchema.validate(req.body)

    if (!validation.error) {
        const newMember = req.body.member;
        const groupId = req.body.groupId;
        const groupIndex = database.groups.findIndex(group => group.id === groupId)

        if (groupIndex === -1) {
            res.status(422)
            res.send("GROUP NOT FOUND BY ID")
        } else {
            console.log(groupIndex)
            console.log(database.groups[groupIndex])
            database.groups[groupIndex].members.push(newMember);

            fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
            res.send("Added " + newMember + " to group " + groupId)
        }
    }
})

router.post('/removeMember', (req, res) => {
    const database = JSON.parse(fs.readFileSync('database.json'))
    const validation = addMemberSchema.validate(req.body)

    if (!validation.error) {
        const memberToRemove = req.body.member;
        const groupId = req.body.groupId;
        const groupIndex = database.groups.findIndex(group => group.id === groupId)

        if (groupIndex === -1) {
            res.status(422)
            res.send("GROUP NOT FOUND BY ID")
        } else {
            const memberIndex = database.groups[groupIndex].members.findIndex(member => member.phonenumber === memberToRemove.phonenumber)
            if (memberIndex === -1) {
                res.status(422)
                res.send("MEMBER NOT FOUND BY NUMBER")
            } else {
                let indexes = [];
                let i = 0;
                database.groups[groupIndex].debts.forEach(debt => {
                    if(debt.borrower.phonenumber === memberToRemove.phonenumber){
                        indexes.push(i);
                    }
                    i = i + 1;
                })

                indexes.forEach(index => {
                    database.groups[groupIndex].debts.splice(index, 1)
                })

                database.groups[groupIndex].members.splice(memberIndex, 1);
                res.send("member " + memberToRemove + " was removed")
                fs.writeFileSync("database.json", JSON.stringify(database, null, 4));
            }
        }
    } else {
        res.status(422);
        res.send(validation.error)
    }
})

module.exports = router;