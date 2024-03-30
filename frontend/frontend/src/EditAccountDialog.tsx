import React from "react";
import { AccountViewModel } from "./types/Account";
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, InputAdornment, Stack, TableCell, TableRow, TextField } from "@mui/material";
import { IconMenu } from "./IconMenu";
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

interface editProps {
    props: AccountViewModel,
    listId: number,
    handleAccountChange: () => void
}
/**
 * Table Row Clickable for edit account
 * @param props accountResult
 * @returns TSX element
 */
export function EditAccountDialog({props, listId, handleAccountChange}: editProps) {
    const { id, accountName, accountUsername, accountPassword } = props;

    const [open, setOpen] = React.useState(false);
    const [showPassword, setShowPassword] = React.useState(false);

    const [Username, setUsername] = React.useState(accountUsername);
    const [Password, setPassword] = React.useState(accountPassword);

    const [error, setError] = React.useState(false);

    const handleClickVisbility = () => {
        setShowPassword((show) => !show);
    }

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setError(false);
        setOpen(false);
    };

    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }

    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }

    const handleSubmit = async() => {
        if (Username.length === 0 || Password.length === 0) {
            setError(true);
        } else {
            const accountView: AccountViewModel = {
                id: id,
                accountName: accountName,
                accountUsername: Username,
                accountPassword: accountPassword
            }
            await fetch("http://localhost:8080/api/account/update", {
                method: 'PUT',
                mode: 'cors',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify(accountView),
                credentials: 'include'
            })

            
            setError(false);
            setOpen(false);
        }
    }

    return (
        <>
            <TableRow key={id} sx={{ cursor: 'pointer' }}>
                <TableCell component="th" scope="row" onClick={handleClickOpen}>
                  {accountName}
                </TableCell>
                <TableCell  onClick={handleClickOpen}>{accountUsername}</TableCell>
                <TableCell onClick={handleClickOpen}>
                  {'*'.repeat(accountPassword.length)}
                </TableCell>
                <TableCell>
                  <IconMenu 
                        account={{
                            id: id, 
                            accountName: accountName,
                            accountUsername: accountUsername, 
                            accountPassword: accountPassword
                        }}
                        listId={listId}
                        handleAccountChange={handleAccountChange}
                    />
                </TableCell>
            </TableRow>

            <Dialog open={open} fullWidth={true}>
                <DialogTitle>Edit Account</DialogTitle>
                <DialogContent>
                    {error && <Alert severity="error">Make sure account details are filled</Alert>}
                    <Stack sx={{paddingTop:"20px"}} spacing={2} component="form" justifyContent="center">
                        <TextField
                            disabled
                            label="Account Name"
                            defaultValue={accountName}
                        />

                        <TextField 
                            label="Username"
                            defaultValue={accountUsername}
                            onChange={handleUsernameChange}
                            required 
                        />
                        
                        <TextField
                            required
                            label="Password"
                            type={showPassword ? 'text' : 'password'}
                            defaultValue={accountPassword}
                            onChange={handlePasswordChange}
                            InputProps={{
                                endAdornment:
                                    <InputAdornment position="end">
                                        <IconButton onClick={handleClickVisbility}>
                                            {showPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                            }} 
                        />
                    </Stack>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button type="submit" onClick={handleSubmit}>Submit</Button>
                </DialogActions>
            </Dialog>
            
        </>
    );
}